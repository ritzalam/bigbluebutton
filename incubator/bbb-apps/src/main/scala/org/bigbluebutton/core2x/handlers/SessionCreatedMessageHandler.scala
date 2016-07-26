package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg.SessionCreatedMessage
import org.bigbluebutton.core2x.models.MeetingStateModel

trait SessionCreatedMessageHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handleSessionCreatedMessage(message: SessionCreatedMessage): Unit = {
    // for {
    //   user <- state.usersModel.findUserWithToken(message.sessionToken)

    //}
  }
}
