package org.bigbluebutton.core

import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.handlers.UsersHandler
import org.bigbluebutton.core.handlers.PresentationHandler
import org.bigbluebutton.core.handlers.PollHandler
import org.bigbluebutton.core.handlers.WhiteboardHandler
import org.bigbluebutton.core.handlers.ChatHandler
import org.bigbluebutton.core.handlers.LayoutHandler
import org.bigbluebutton.core.handlers.BreakoutRoomHandler
import org.bigbluebutton.core.models._
import java.util.concurrent.TimeUnit
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.bus.IncomingEventBus

import akka.actor.ActorContext
import akka.event.Logging
import org.bigbluebutton.core.handlers.CaptionHandler
import org.bigbluebutton.core.filters.UsersHandlerFilter

class LiveMeeting(val mProps: MeetingProperties,
  val eventBus: IncomingEventBus,
  val outGW: OutMessageGateway,
  val chatModel: ChatModel,
  val layoutModel: LayoutModel,
  val pollModel: PollModel,
  val wbModel: WhiteboardModel,
  val presModel: PresentationModel,
  val breakoutModel: BreakoutRoomModel,
  val captionModel: CaptionModel)(implicit val context: ActorContext)
    extends PresentationHandler
    with LayoutHandler with ChatHandler with WhiteboardHandler with PollHandler
    with BreakoutRoomHandler with CaptionHandler with UsersHandlerFilter {

  val log = Logging(context.system, getClass)

  object Meeting extends Meeting
  val meeting = Meeting
  //  object RegisteredUsers extends RegisteredUsers
  //  val registeredUsers = RegisteredUsers

  def hasMeetingEnded(): Boolean = {
    meeting.hasMeetingEnded()
  }

  def webUserJoined() {
    if (meeting.numWebUsers > 0) {
      meeting.resetLastWebUserLeftOn()
    }
  }

  def startRecordingIfAutoStart() {
    if (mProps.recorded.value && !meeting.isRecording() && mProps.autoStartRecording && meeting.numWebUsers == 1) {
      log.info("Auto start recording. meetingId={}", mProps.id)
      meeting.recordingStarted()
      outGW.send(new RecordingStatusChanged(mProps.id, mProps.recorded, IntUserId("system"), meeting.isRecording()))
    }
  }

  def stopAutoStartedRecording() {
    if (mProps.recorded.value && meeting.isRecording() && mProps.autoStartRecording && meeting.numWebUsers == 0) {
      log.info("Last web user left. Auto stopping recording. meetingId={}", mProps.id)
      meeting.recordingStopped()
      outGW.send(new RecordingStatusChanged(mProps.id, mProps.recorded, IntUserId("system"), meeting.isRecording()))
    }
  }

  def startCheckingIfWeNeedToEndVoiceConf() {
    if (meeting.numWebUsers == 0) {
      meeting.lastWebUserLeft()
      log.debug("MonitorNumberOfWebUsers started for meeting [" + mProps.id + "]")
    }
  }

  def sendTimeRemainingNotice() {
    val now = timeNowInSeconds

    if (mProps.duration > 0 && (((meeting.startedOn + mProps.duration) - now) < 15)) {
      //  log.warning("MEETING WILL END IN 15 MINUTES!!!!")
    }
  }

  def handleMonitorNumberOfWebUsers(msg: MonitorNumberOfUsers) {
    if (meeting.numWebUsers == 0 && meeting.lastWebUserLeftOn > 0) {
      if (timeNowInMinutes - meeting.lastWebUserLeftOn > 2) {
        log.info("Empty meeting. Ejecting all users from voice. meetingId={}", mProps.id)
        outGW.send(new EjectAllVoiceUsers(mProps.id, mProps.recorded, mProps.voiceBridge))
      }
    }
  }

  def handleSendTimeRemainingUpdate(msg: SendTimeRemainingUpdate) {
    if (mProps.duration > 0) {
      val endMeetingTime = meeting.startedOn + (mProps.duration * 60)
      val timeRemaining = endMeetingTime - timeNowInSeconds
      outGW.send(new MeetingTimeRemainingUpdate(mProps.id, mProps.recorded, timeRemaining.toInt))
    }
    if (!mProps.isBreakout && breakoutModel.getRooms().length > 0) {
      val room = breakoutModel.getRooms()(0);
      val endMeetingTime = meeting.breakoutRoomsStartedOn + (meeting.breakoutRoomsdurationInMinutes * 60)
      val timeRemaining = endMeetingTime - timeNowInSeconds
      outGW.send(new BreakoutRoomsTimeRemainingUpdateOutMessage(mProps.id, mProps.recorded, timeRemaining.toInt))
    } else if (meeting.breakoutRoomsStartedOn != 0) {
      meeting.breakoutRoomsdurationInMinutes = 0;
      meeting.breakoutRoomsStartedOn = 0;
    }
  }

  def handleExtendMeetingDuration(msg: ExtendMeetingDuration) {

  }

  def timeNowInMinutes(): Long = {
    TimeUnit.NANOSECONDS.toMinutes(System.nanoTime())
  }

  def timeNowInSeconds(): Long = {
    TimeUnit.NANOSECONDS.toSeconds(System.nanoTime())
  }

  def handleEndMeeting(msg: EndMeeting) {
    meeting.meetingHasEnded
    outGW.send(new MeetingEnded(msg.meetingId, mProps.recorded, mProps.voiceBridge.value))
    outGW.send(new DisconnectAllUsers(msg.meetingId))
  }

  def handleVoiceConfRecordingStartedMessage(msg: VoiceConfRecordingStartedMessage) {
    if (msg.recording) {
      meeting.setVoiceRecordingFilename(msg.recordStream)
      outGW.send(new VoiceRecordingStarted(mProps.id, mProps.recorded,
        msg.recordStream, msg.timestamp, mProps.voiceBridge.value))
    } else {
      meeting.setVoiceRecordingFilename("")
      outGW.send(new VoiceRecordingStopped(mProps.id, mProps.recorded,
        msg.recordStream, msg.timestamp, mProps.voiceBridge.value))
    }
  }

  def handleSetRecordingStatus(msg: SetRecordingStatus) {
    log.info("Change recording status. meetingId=" + mProps.id + " recording=" + msg.recording)
    if (mProps.allowStartStopRecording && meeting.isRecording() != msg.recording) {
      if (msg.recording) {
        meeting.recordingStarted()
      } else {
        meeting.recordingStopped()
      }

      outGW.send(new RecordingStatusChanged(mProps.id, mProps.recorded, msg.userId, msg.recording))
    }
  }

  def handleGetRecordingStatus(msg: GetRecordingStatus) {
    outGW.send(new GetRecordingStatusReply(mProps.id, mProps.recorded, msg.userId, meeting.isRecording().booleanValue()))
  }

  def lockLayout(lock: Boolean) {
    meeting.lockLayout(lock)
  }

  def newPermissions(np: Permissions) {
    meeting.setPermissions(np)
  }

  def permissionsEqual(other: Permissions): Boolean = {
    meeting.permissionsEqual(other)
  }
}
