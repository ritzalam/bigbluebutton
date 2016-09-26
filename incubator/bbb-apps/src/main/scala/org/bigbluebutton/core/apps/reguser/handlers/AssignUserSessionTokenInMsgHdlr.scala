package org.bigbluebutton.core.apps.reguser.handlers

import org.bigbluebutton.core.api.IncomingMsg.AssignUserSessionTokenInMsg2x
import org.bigbluebutton.core.api.OutGoingMsg.{ UserSessionTokenAssignedBody, UserSessionTokenAssignedMsg, UserSessionTokenAssignedOutMsg2x }
import org.bigbluebutton.core.api.{ BroadcastMsgType, MsgHeader, OutGoingMsg }
import org.bigbluebutton.core.apps.reguser.RegisteredUserActor
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }

trait AssignUserSessionTokenInMsgHdlr {
  this: RegisteredUserActor =>

  def handle(msg: AssignUserSessionTokenInMsg2x): Unit = {
    sessionTokens + msg.body.sessionToken.value
    val header = MsgHeader(OutGoingMsg.UserSessionTokenAssignedOutMsgName, BroadcastMsgType, meetingId, userId, None)
    val body = UserSessionTokenAssignedBody(IntMeetingId(meetingId), IntUserId(userId), msg.body.sessionToken)
    val om = UserSessionTokenAssignedMsg(OutGoingMsg.UserSessionTokenAssignedOutMsgName, body)
    outGW.send(UserSessionTokenAssignedOutMsg2x(header, om))

  }
}
