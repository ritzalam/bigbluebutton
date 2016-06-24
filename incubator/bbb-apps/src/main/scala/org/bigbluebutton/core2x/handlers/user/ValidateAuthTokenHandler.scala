package org.bigbluebutton.core2x.handlers.user

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage.ValidateAuthTokenRequestInMessage
import org.bigbluebutton.core2x.api.OutGoingMessage.{ ValidateAuthTokenReply2x, ValidateAuthTokenSuccessReplyOutMessage }
import org.bigbluebutton.core2x.domain.{ DialNumber, _ }
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsers2x }

trait ValidateAuthTokenHandler {
  val outGW: OutMessageGateway

  def handleValidateAuthToken2x(msg: ValidateAuthTokenRequestInMessage, meeting: MeetingStateModel): Unit = {
    def sendResponse(user: RegisteredUser2x): Unit = {
      val reply = new ValidateAuthTokenSuccessReplyOutMessage(
        msg.meetingId, msg.userId, user.name, user.roles,
        user.extId, user.authToken, user.avatar,
        user.logoutUrl, user.welcome, user.dialNumbers,
        user.config, user.extData)
      outGW.send(reply)
    }

    for {
      user <- RegisteredUsers2x.findWithToken(msg.token, meeting.registeredUsers.toVector)
    } yield sendResponse(user)
  }
}
