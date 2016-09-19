package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.{ OutMessageGateway, UserHandlers }
import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core.api.OutGoingMsg.DisconnectUser2x
import org.bigbluebutton.core.domain.RegisteredUser
import org.bigbluebutton.core.meeting.MeetingActorMsg
import org.bigbluebutton.core.meeting.models.MeetingStateModel
import org.bigbluebutton.core.reguser.RegisteredUsersModel

trait ValidateAuthTokenCommandMsgHdlr {
  this: MeetingActorMsg =>

  val state: MeetingStateModel
  val outGW: OutMessageGateway
  val userHandlers: UserHandlers

  def handle(msg: ValidateAuthTokenInMessage): Unit = {
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

trait ValidateAuthTokenCommandMsgFilter extends ValidateAuthTokenCommandMsgHdlr {
  this: MeetingActorMsg =>

  abstract override def handle(msg: ValidateAuthTokenInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.token, state.registeredUsersModel.toVector) match {
      case Some(u) =>
        log.debug("Received ValidateAuthTokenRequestInMessage. Filter passed. Forwarding.")
        super.handle(msg)
      case None =>
        log.debug("Received ValidateAuthTokenRequestInMessage. Filter failed. Disconnecting.")
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
    }
  }
}