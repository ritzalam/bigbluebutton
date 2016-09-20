package org.bigbluebutton.core.apps.user.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core.api.OutGoingMsg.ValidateAuthTokenSuccessReplyOutMsg
import org.bigbluebutton.core.apps.reguser.RegisteredUsersModel
import org.bigbluebutton.core.domain.RegisteredUser
import org.bigbluebutton.core.meeting.models.MeetingStateModel

trait ValidateAuthTokenMsgHdlr {
  this: UserInMsgHdlr =>

  val outGW: OutMessageGateway

  def handle(msg: ValidateAuthTokenInMessage, meeting: MeetingStateModel): Unit = {
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
