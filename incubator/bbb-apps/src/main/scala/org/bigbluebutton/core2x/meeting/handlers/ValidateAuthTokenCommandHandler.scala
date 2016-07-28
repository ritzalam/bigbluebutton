package org.bigbluebutton.core2x.meeting.handlers

import org.bigbluebutton.core2x.{ OutMessageGateway, UserHandlers }
import org.bigbluebutton.core2x.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core2x.api.OutGoingMsg.DisconnectUser2x
import org.bigbluebutton.core2x.domain.RegisteredUser2x
import org.bigbluebutton.core2x.meeting.MeetingActor2x
import org.bigbluebutton.core2x.meeting.models.{ MeetingStateModel, RegisteredUsersModel }

trait ValidateAuthTokenCommandHandler {
  this: MeetingActor2x =>

  val state: MeetingStateModel
  val outGW: OutMessageGateway
  val userHandlers: UserHandlers

  def handleValidateAuthToken2x(msg: ValidateAuthTokenInMessage): Unit = {
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