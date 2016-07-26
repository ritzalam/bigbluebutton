package org.bigbluebutton.core2x.filters

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg._
import org.bigbluebutton.core2x.api.OutGoingMsg._
import org.bigbluebutton.core2x.domain.{ CanEjectUser }
import org.bigbluebutton.core2x.handlers.{ UsersHandler2x }
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsersModel, UsersModel }

trait UsersHandlerFilter extends UsersHandler2x {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val abilitiesFilter = DefaultAbilitiesFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeetingInMsg): Unit = {
    UsersModel.findWithId(msg.ejectedBy, state.usersModel.toVector) foreach { user =>

      val abilities = abilitiesFilter.calcEffectiveAbilities(
        user.roles,
        user.permissions,
        state.abilities.get.removed)

      if (abilitiesFilter.can(CanEjectUser, abilities)) {
        super.handleEjectUserFromMeeting(msg)
      } else {
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.ejectedBy))
      }
    }
  }

  abstract override def handleValidateAuthToken2x(msg: ValidateAuthTokenInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.token, state.registeredUsersModel.toVector) match {
      case Some(u) =>
        super.handleValidateAuthToken2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
    }
  }

  abstract override def handleUserJoinWeb2x(msg: UserJoinMeetingInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.sessionToken, state.registeredUsersModel.toVector) match {
      case Some(u) =>
        super.handleUserJoinWeb2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
    }
  }
}