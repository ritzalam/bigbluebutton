package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.BigBlueButtonInMessage
import org.bigbluebutton.core.api.IncomingMsg.AssignUserSessionTokenInMsg2x
import org.bigbluebutton.core.api.OutGoingMsg.{ UserSessionTokenAssignedBody, UserSessionTokenAssignedMsg, UserSessionTokenAssignedOutMsg2x }
import org.bigbluebutton.core.api.{ BroadcastMsgType, MsgHeader, OutGoingMsg }
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.meeting.MeetingActorMsg

trait AssignUserSessionTokenInMsgHdlr {
  this: MeetingActorMsg =>

  def handle(msg: AssignUserSessionTokenInMsg2x): Unit = {
    val dest = msg.body.userId.value + "@" + msg.body.meetingId.value
    val header = msg.header.copy(dest = dest)
    val fm = msg.copy(header = header)
    bus.publish(BigBlueButtonInMessage(dest, fm))
  }
}
