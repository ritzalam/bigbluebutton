package org.bigbluebutton.core2x.meeting.handlers.user

import org.bigbluebutton.core2x.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core2x.api.OutGoingMsg.{ ValidateAuthTokenReply2x, ValidateAuthTokenSuccessReplyOutMsg }
import org.bigbluebutton.core2x.domain.{ DialNumber, _ }
import org.bigbluebutton.core2x.meeting.models.{ MeetingStateModel, RegisteredUsersModel }

trait ValidateAuthTokenHandler {
  this: UserActorMessageHandler =>

  val outGW: OutMessageGateway

  def handleValidateAuthToken2x(msg: ValidateAuthTokenInMessage, meeting: MeetingStateModel): Unit = {
    def sendResponse(user: RegisteredUser2x): Unit = {
      val reply = new ValidateAuthTokenSuccessReplyOutMsg(
        msg.meetingId, msg.senderId, user.name, user.roles,
        user.extId, user.authToken, user.avatar,
        user.logoutUrl, user.welcome, user.dialNumbers,
        user.config, user.extData)
      outGW.send(reply)
    }

    for {
      user <- RegisteredUsersModel.findWithToken(msg.token, meeting.registeredUsersModel.toVector)
    } yield sendResponse(user)
  }
}
