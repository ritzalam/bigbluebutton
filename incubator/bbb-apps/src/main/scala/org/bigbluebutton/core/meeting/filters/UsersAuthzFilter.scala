package org.bigbluebutton.core.meeting.filters

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.api.OutGoingMsg._
import org.bigbluebutton.core.domain.CanEjectUser
import org.bigbluebutton.core.meeting.handlers.UsersHandler2x
import org.bigbluebutton.core.meeting.models.MeetingStateModel
import org.bigbluebutton.core.reguser.RegisteredUsersModel
import org.bigbluebutton.core.user.UsersModel

trait UsersHandlerFilter extends UsersHandler2x {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val abilitiesFilter = DefaultAbilitiesFilter

  def handleEjectUserFromMeeting(msg: EjectUserFromMeetingInMsg): Unit = {
    UsersModel.findWithId(msg.ejectedBy, state.usersModel.toVector) foreach { user =>

      val abilities = abilitiesFilter.calcEffectiveAbilities(
        user.roles,
        user.permissions,
        state.abilities.get.removed)

      if (abilitiesFilter.can(CanEjectUser, abilities)) {
        //super.handleEjectUserFromMeeting(msg)
      } else {
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.ejectedBy))
      }
    }
  }

  def handleValidateAuthToken2x(msg: ValidateAuthTokenInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.token, state.registeredUsersModel.toVector) match {
      case Some(u) =>
      //super.handleValidateAuthToken2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
    }
  }

  def handleUserJoinWeb2x(msg: UserJoinMeetingInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.sessionToken, state.registeredUsersModel.toVector) match {
      case Some(u) =>
      //super.handleUserJoinWeb2x(msg)
      //outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
    }
  }
}