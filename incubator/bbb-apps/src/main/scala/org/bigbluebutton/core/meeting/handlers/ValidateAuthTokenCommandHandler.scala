package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.{ OutMessageGateway, UserHandlers }
import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core.api.OutGoingMsg.DisconnectUser2x
import org.bigbluebutton.core.domain.RegisteredUser
import org.bigbluebutton.core.meeting.MeetingActor
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, RegisteredUsersModel }

trait ValidateAuthTokenCommandHandler {
  this: MeetingActor =>

  val state: MeetingStateModel
  val outGW: OutMessageGateway
  val userHandlers: UserHandlers

  def handleValidateAuthToken2x(msg: ValidateAuthTokenInMessage): Unit = {
    log.debug("Received ValidateAuthTokenRequestInMessage")
    def handle(regUser: RegisteredUser): Unit = {
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
  this: MeetingActor =>

  abstract override def handleValidateAuthToken2x(msg: ValidateAuthTokenInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.token, state.registeredUsersModel.toVector) match {
      case Some(u) =>
        log.debug("Received ValidateAuthTokenRequestInMessage. Filter passed. Forwarding.")
        super.handleValidateAuthToken2x(msg)
      case None =>
        log.debug("Received ValidateAuthTokenRequestInMessage. Filter failed. Disconnecting.")
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
    }
  }
}