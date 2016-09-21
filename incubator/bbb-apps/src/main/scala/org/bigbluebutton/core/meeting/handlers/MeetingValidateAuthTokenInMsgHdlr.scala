package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.{ BigBlueButtonInMessage, OutMessageGateway }
import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMsg2x
import org.bigbluebutton.core.meeting.MeetingActorMsg

trait MeetingValidateAuthTokenInMsgHdlr {
  this: MeetingActorMsg =>

  val outGW: OutMessageGateway

  def handle(msg: ValidateAuthTokenInMsg2x): Unit = {

  }
}
