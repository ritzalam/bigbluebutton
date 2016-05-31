package org.bigbluebutton.core2x.handlers.user

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage.ValidateAuthToken
import org.bigbluebutton.core2x.api.OutGoingMessage.ValidateAuthTokenReply2x
import org.bigbluebutton.core2x.domain.RegisteredUser2x
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsers2x }

trait ValidateAuthTokenHandler {
  val outGW: OutMessageGateway

  def handleValidateAuthToken2x(msg: ValidateAuthToken, meeting: MeetingStateModel): Unit = {
    def sendResponse(user: RegisteredUser2x): Unit = {
      // TODO: Send response with user status
      outGW.send(new ValidateAuthTokenReply2x(msg.meetingId, msg.userId, msg.token, true))

    }

    for {
      user <- RegisteredUsers2x.findWithToken(msg.token, meeting.registeredUsers.toVector)
    } yield sendResponse(user)
  }
}
