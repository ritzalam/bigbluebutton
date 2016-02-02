package org.bigbluebutton.core

import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.handlers.UsersHandler
import org.bigbluebutton.core.handlers.PresentationHandler
import org.bigbluebutton.core.handlers.PollHandler
import org.bigbluebutton.core.handlers.WhiteboardHandler
import org.bigbluebutton.core.handlers.ChatHandler
import org.bigbluebutton.core.handlers.LayoutHandler
import org.bigbluebutton.core.handlers.BreakoutRoomHandler
import org.bigbluebutton.core.models.ChatModel
import org.bigbluebutton.core.models.LayoutModel
import org.bigbluebutton.core.models.UsersModel
import org.bigbluebutton.core.models.PollModel
import org.bigbluebutton.core.models.WhiteboardModel
import org.bigbluebutton.core.models.PresentationModel
import org.bigbluebutton.core.models.BreakoutRoomModel
import org.bigbluebutton.core.api._
import akka.actor.ActorContext
import akka.actor.ActorSystem
import java.util.concurrent.TimeUnit
import akka.event.Logging
import org.bigbluebutton.core.handlers.CaptionHandler
import org.bigbluebutton.core.models.CaptionModel
import org.bigbluebutton.core.filters.AuthorizationFilter

class LiveMeeting(val mProps: MeetingProperties,
  val eventBus: IncomingEventBus,
  val outGW: OutMessageGateway,
  val chatModel: ChatModel,
  val layoutModel: LayoutModel,
  val meetingModel: MeetingModel,
  val usersModel: UsersModel,
  val pollModel: PollModel,
  val wbModel: WhiteboardModel,
  val presModel: PresentationModel,
  val breakoutModel: BreakoutRoomModel,
  val captionModel: CaptionModel)(implicit val context: ActorContext)
    extends UsersHandler with PresentationHandler
    with LayoutHandler with ChatHandler with WhiteboardHandler with PollHandler
    with BreakoutRoomHandler with CaptionHandler with AuthorizationFilter {

  val log = Logging(context.system, getClass)

  def hasMeetingEnded(): Boolean = {
    meetingModel.hasMeetingEnded()
  }

  def webUserJoined() {
    if (usersModel.numWebUsers > 0) {
      meetingModel.resetLastWebUserLeftOn()
    }
  }

  def startRecordingIfAutoStart() {
    if (mProps.recorded && !meetingModel.isRecording() && mProps.autoStartRecording && usersModel.numWebUsers == 1) {
      log.info("Auto start recording. meetingId={}", mProps.meetingID)
      meetingModel.recordingStarted()
      outGW.send(new RecordingStatusChanged(mProps.meetingID, mProps.recorded, "system", meetingModel.isRecording()))
    }
  }

  def stopAutoStartedRecording() {
    if (mProps.recorded && meetingModel.isRecording() && mProps.autoStartRecording && usersModel.numWebUsers == 0) {
      log.info("Last web user left. Auto stopping recording. meetingId={}", mProps.meetingID)
      meetingModel.recordingStopped()
      outGW.send(new RecordingStatusChanged(mProps.meetingID, mProps.recorded, "system", meetingModel.isRecording()))
    }
  }

  def startCheckingIfWeNeedToEndVoiceConf() {
    if (usersModel.numWebUsers == 0) {
      meetingModel.lastWebUserLeft()
      log.debug("MonitorNumberOfWebUsers started for meeting [" + mProps.meetingID + "]")
    }
  }

  def sendTimeRemainingNotice() {
    val now = timeNowInMinutes

    if (mProps.duration > 0 && (((meetingModel.startedOn + mProps.duration) - now) < 15)) {
      //  log.warning("MEETING WILL END IN 15 MINUTES!!!!")
    }
  }

  def handleMonitorNumberOfWebUsers(msg: MonitorNumberOfUsers) {
    if (usersModel.numWebUsers == 0 && meetingModel.lastWebUserLeftOn > 0) {
      if (timeNowInMinutes - meetingModel.lastWebUserLeftOn > 2) {
        log.info("Empty meeting. Ejecting all users from voice. meetingId={}", mProps.meetingID)
        outGW.send(new EjectAllVoiceUsers(mProps.meetingID, mProps.recorded, mProps.voiceBridge))
      }
    }
  }

  def calculateTimeRemaining(): Int = {
    val endMeetingTime = meetingModel.startedOn + mProps.duration
    val timeRemaining = endMeetingTime - timeNowInMinutes()
    timeRemaining.toInt
  }

  def handleSendTimeRemainingUpdate(msg: SendTimeRemainingUpdate) {
    if (mProps.duration > 0) {
      val endMeetingTime = meetingModel.startedOn + mProps.duration
      val timeRemaining = endMeetingTime - timeNowInMinutes()
      outGW.send(new MeetingTimeRemainingUpdate(mProps.meetingID, mProps.recorded, timeRemaining.toInt))
    }

  }

  def handleExtendMeetingDuration(msg: ExtendMeetingDuration) {

  }

  def timeNowInMinutes(): Long = {
    TimeUnit.NANOSECONDS.toMinutes(System.nanoTime())
  }

  def sendMeetingHasEnded(userId: String) {
    outGW.send(new MeetingHasEnded(mProps.meetingID, userId))
    outGW.send(new DisconnectUser(mProps.meetingID, userId))
  }

  def handleEndMeeting(msg: EndMeeting) {
    meetingModel.meetingHasEnded
    outGW.send(new MeetingEnded(msg.meetingId, mProps.recorded, mProps.voiceBridge))
    outGW.send(new DisconnectAllUsers(msg.meetingId))
  }

  def handleVoiceConfRecordingStartedMessage(msg: VoiceConfRecordingStartedMessage) {
    if (msg.recording) {
      meetingModel.setVoiceRecordingFilename(msg.recordStream)
      outGW.send(new VoiceRecordingStarted(mProps.meetingID, mProps.recorded, msg.recordStream, msg.timestamp, mProps.voiceBridge))
    } else {
      meetingModel.setVoiceRecordingFilename("")
      outGW.send(new VoiceRecordingStopped(mProps.meetingID, mProps.recorded, msg.recordStream, msg.timestamp, mProps.voiceBridge))
    }
  }

  def handleSetRecordingStatus(msg: SetRecordingStatus) {
    log.info("Change recording status. meetingId=" + mProps.meetingID + " recording=" + msg.recording)
    if (mProps.allowStartStopRecording && meetingModel.isRecording() != msg.recording) {
      if (msg.recording) {
        meetingModel.recordingStarted()
      } else {
        meetingModel.recordingStopped()
      }

      outGW.send(new RecordingStatusChanged(mProps.meetingID, mProps.recorded, msg.userId, msg.recording))
    }
  }

  def handleGetRecordingStatus(msg: GetRecordingStatus) {
    outGW.send(new GetRecordingStatusReply(mProps.meetingID, mProps.recorded, msg.userId, meetingModel.isRecording().booleanValue()))
  }

  def lockLayout(lock: Boolean) {
    meetingModel.lockLayout(lock)
  }

  def newPermissions(np: Permissions) {
    meetingModel.setPermissions(np)
  }

  def permissionsEqual(other: Permissions): Boolean = {
    meetingModel.permissionsEqual(other)
  }
}