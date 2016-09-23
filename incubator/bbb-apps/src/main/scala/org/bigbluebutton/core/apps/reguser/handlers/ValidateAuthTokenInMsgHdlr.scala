package org.bigbluebutton.core.apps.reguser.handlers

import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMsg2x
import org.bigbluebutton.core.apps.reguser.RegisteredUserActor
import org.bigbluebutton.core.api.OutGoingMsg.{ ValidateAuthTokenReplyBody, ValidateAuthTokenReplyMsg, ValidateAuthTokenReplyOutMsg2x }
import org.bigbluebutton.core.api.{ DirectMsgType, MsgHeader, OutGoingMsg }
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }

trait ValidateAuthTokenInMsgHdlr {
  this: RegisteredUserActor =>

  def handle(msg: ValidateAuthTokenInMsg2x): Unit = {
    if (sessionTokens.contains(msg.body.token)) {
      val header = MsgHeader(OutGoingMsg.ValidateAuthTokenReplyOutMsgName, DirectMsgType, meetingId, userId, None)
      val body = ValidateAuthTokenReplyBody(IntMeetingId(meetingId), IntUserId(userId), msg.body.token, true)
      val om = ValidateAuthTokenReplyMsg(OutGoingMsg.ValidateAuthTokenReplyOutMsgName, body)
      outGW.send(ValidateAuthTokenReplyOutMsg2x(header, om))

    } else {
      val header = MsgHeader(OutGoingMsg.ValidateAuthTokenReplyOutMsgName, DirectMsgType, meetingId, userId, None)
      val body = ValidateAuthTokenReplyBody(IntMeetingId(meetingId), IntUserId(userId), msg.body.token, false)
      val om = ValidateAuthTokenReplyMsg(OutGoingMsg.ValidateAuthTokenReplyOutMsgName, body)
      outGW.send(ValidateAuthTokenReplyOutMsg2x(header, om))
    }
  }
}
