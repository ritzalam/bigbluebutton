package org.bigbluebutton.core.meetingsmanager.handlers

import org.bigbluebutton.core.{ IncomingEventBus2x, OutMessageGateway }
import org.bigbluebutton.core.api.IncomingMsg.CreateMeetingRequestInMsg
import org.bigbluebutton.core.api.OutGoingMsg.MeetingCreatedEventOutMsg
import org.bigbluebutton.core.meeting.RunningMeeting
import org.bigbluebutton.core.meetingsmanager.BigBlueButtonActor

trait CreateMeetingInMsgHdlr {
  this: BigBlueButtonActor =>

  val eventBus: IncomingEventBus2x
  val outGW: OutMessageGateway

  def handleCreateMeeting(msg: CreateMeetingRequestInMsg): Unit = {
    meetings.get(msg.meetingId.value) match {
      case None =>
        log.info("Create meeting request. meetingId={}", msg.mProps.id)
        val m = RunningMeeting(msg.mProps, outGW, eventBus)
        meetings += m.mProps.id.value -> m
        outGW.send(new MeetingCreatedEventOutMsg(m.mProps.id, m.mProps))
      case Some(m) =>
        log.info("Meeting already created. meetingId={}", msg.mProps.id)
      // do nothing
    }
  }
}
