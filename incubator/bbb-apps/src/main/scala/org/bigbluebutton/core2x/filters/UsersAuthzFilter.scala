package org.bigbluebutton.core2x.filters

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage._
import org.bigbluebutton.core2x.api.OutGoingMessage._
import org.bigbluebutton.core2x.domain.{ CanEjectUser }
import org.bigbluebutton.core2x.handlers.{ UsersHandler2x }
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsers2x, Users3x }

trait UsersHandlerFilter extends UsersHandler2x {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val abilitiesFilter = DefaultAbilitiesFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting): Unit = {
    Users3x.findWithId(msg.ejectedBy, state.users.toVector) foreach { user =>

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

  abstract override def handleValidateAuthToken2x(msg: ValidateAuthTokenRequestInMessage): Unit = {
    RegisteredUsers2x.findWithToken(msg.token, state.registeredUsers.toVector) match {
      case Some(u) =>
        super.handleValidateAuthToken2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
    }
  }

  abstract override def handleUserJoinWeb2x(msg: NewUserPresence2x): Unit = {
    RegisteredUsers2x.findWithToken(msg.token, state.registeredUsers.toVector) match {
      case Some(u) =>
        super.handleUserJoinWeb2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
    }
  }
}