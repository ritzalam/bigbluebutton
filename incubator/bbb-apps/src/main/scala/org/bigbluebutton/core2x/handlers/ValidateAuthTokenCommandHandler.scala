package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.{ MeetingActor2x, UserHandlers }
import org.bigbluebutton.core2x.api.IncomingMessage.ValidateAuthTokenRequestInMessage
import org.bigbluebutton.core2x.api.OutGoingMessage.DisconnectUser2x
import org.bigbluebutton.core2x.domain.RegisteredUser2x
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsersModel }

trait ValidateAuthTokenCommandHandler {
  this: MeetingActor2x =>

  val state: MeetingStateModel
  val outGW: OutMessageGateway
  val userHandlers: UserHandlers

  def handleValidateAuthToken2x(msg: ValidateAuthTokenRequestInMessage): Unit = {
    log.debug("Received ValidateAuthTokenRequestInMessage")
    def handle(regUser: RegisteredUser2x): Unit = {
      val userHandler = userHandlers.createHandler(regUser, outGW)
      log.debug("Handing off to user handler.")
      userHandler.handleValidateAuthToken2x(msg, state)
    }

    for {
      regUser <- RegisteredUsersModel.findWithToken(msg.token, state.registeredUsersModel.toVector)
    } yield handle(regUser)

  }
}

trait ValidateAuthTokenCommandFilter extends ValidateAuthTokenCommandHandler {
  this: MeetingActor2x =>

  abstract override def handleValidateAuthToken2x(msg: ValidateAuthTokenRequestInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.token, state.registeredUsersModel.toVector) match {
      case Some(u) =>
        log.debug("Received ValidateAuthTokenRequestInMessage. Filter passed. Forwarding.")
        super.handleValidateAuthToken2x(msg)
      case None =>
        log.debug("Received ValidateAuthTokenRequestInMessage. Filter failed. Disconnecting.")
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
    }
  }
}