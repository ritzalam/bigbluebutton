package org.bigbluebutton.core2x.handlers.user

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage.ValidateAuthTokenRequestInMessage
import org.bigbluebutton.core2x.api.OutGoingMessage.{ ValidateAuthTokenReply2x, ValidateAuthTokenSuccessReplyOutMessage }
import org.bigbluebutton.core2x.domain.{ DialNumber, _ }
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsersModel }

trait ValidateAuthTokenHandler {
  this: UserActorMessageHandler =>

  val outGW: OutMessageGateway

  def handleValidateAuthToken2x(msg: ValidateAuthTokenRequestInMessage, meeting: MeetingStateModel): Unit = {

    def sendResponse(user: RegisteredUser2x): Unit = {
      val reply = new ValidateAuthTokenSuccessReplyOutMessage(
        msg.meetingId, msg.userId, user.name, user.roles,
        user.extId, user.authToken, user.avatar,
        user.logoutUrl, user.welcome, user.dialNumbers,
        user.config, user.extData)
      println("Sending ValidateAuthTokenSuccessReplyOutMessage")
      outGW.send(reply)
    }

    for {
      user <- RegisteredUsersModel.findWithToken(msg.token, meeting.registeredUsersModel.toVector)
    } yield sendResponse(user)
  }
}
