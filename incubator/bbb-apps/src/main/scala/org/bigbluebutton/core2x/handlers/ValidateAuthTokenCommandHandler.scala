package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.UserHandlers
import org.bigbluebutton.core2x.api.IncomingMessage.ValidateAuthToken
import org.bigbluebutton.core2x.api.OutGoingMessage.DisconnectUser2x
import org.bigbluebutton.core2x.domain.RegisteredUser2x
import org.bigbluebutton.core2x.handlers.user.UserActorMessageHandler
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsers2x }

trait ValidateAuthTokenCommandHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway
  val userHandlers: UserHandlers

  def handleValidateAuthToken2x(msg: ValidateAuthToken): Unit = {
    def handle(regUser: RegisteredUser2x): Unit = {
      val userHandler = new UserActorMessageHandler(regUser, outGW)
      userHandlers.createHandler(regUser, outGW)
      userHandler.handleValidateAuthToken2x(msg, state)
    }

    for {
      regUser <- RegisteredUsers2x.findWithToken(msg.token, state.registeredUsers.toVector)
    } yield handle(regUser)

  }
}

trait ValidateAuthTokenCommandFilter extends ValidateAuthTokenCommandHandler {
  abstract override def handleValidateAuthToken2x(msg: ValidateAuthToken): Unit = {
    RegisteredUsers2x.findWithToken(msg.token, state.registeredUsers.toVector) match {
      case Some(u) =>
        super.handleValidateAuthToken2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
    }
  }
}