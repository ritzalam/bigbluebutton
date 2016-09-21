package org.bigbluebutton.core.apps.reguser.handlers

import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMsg2x
import org.bigbluebutton.core.api.OutGoingMsg.DisconnectUser2x
import org.bigbluebutton.core.apps.reguser.{ RegisteredUserActor, RegisteredUsersModel }
import org.bigbluebutton.core.{ BigBlueButtonInMessage, OutMessageGateway }

trait RegUserValidateAuthTokenInMsgHdlr {
  this: RegisteredUserActor =>

  val outGW: OutMessageGateway

  def handle(msg: ValidateAuthTokenInMsg2x): Unit = {
    if (sessionTokens.contains(msg.body.token.value)) {
      inGW.publish(BigBlueButtonInMessage(meetingId.value, msg))
    } else {
      // TODO: send validate token failed
    }
  }
}
