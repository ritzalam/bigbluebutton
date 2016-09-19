package org.bigbluebutton.core.user.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core.api.OutGoingMsg.ValidateAuthTokenSuccessReplyOutMsg
import org.bigbluebutton.core.domain.RegisteredUser
import org.bigbluebutton.core.meeting.models.MeetingStateModel
import org.bigbluebutton.core.reguser.RegisteredUsersModel

trait ValidateAuthTokenMsgHdlr {
  this: UserInMsgHdlr =>

  val outGW: OutMessageGateway

  def handleValidateAuthTokenInMessage(msg: ValidateAuthTokenInMessage, meeting: MeetingStateModel): Unit = {
    def sendResponse(user: RegisteredUser): Unit = {
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
