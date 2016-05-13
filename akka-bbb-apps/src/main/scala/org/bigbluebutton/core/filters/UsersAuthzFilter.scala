package org.bigbluebutton.core.filters

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.domain.{ CanEjectUser, MeetingProperties2x }
import org.bigbluebutton.core.handlers.{ UserActorMessageHandler, UsersHandler, UsersHandler2x }
import org.bigbluebutton.core.models.{ Meeting2x, MeetingState, RegisteredUsers2x, Users3x }

trait UsersHandlerFilter extends UsersHandler2x {
  val state: MeetingState
  val props: MeetingProperties2x
  val outGW: OutMessageGateway

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val abilitiesFilter = DefaultAbilitiesFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting): Unit = {
    /*    meeting.state.users.findWithId(msg.userId) foreach { user =>
      val abilities = abilitiesFilter.calcEffectiveAbilities(user.roles, user.permissions, meeting.getPermissions)
      if (abilitiesFilter.can(CanEjectUser, abilities)) {
        // forward message to handler to process
        super.handleEjectUserFromMeeting(msg)
      } else {
        //     send request rejected message
      }
    }
*/
  }

  override def handleValidateAuthToken2x(msg: ValidateAuthToken): Unit = {
    RegisteredUsers2x.findWithToken(msg.token, state.registeredUsers.toVector) match {
      case Some(u) =>
        super.handleValidateAuthToken2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
    }
  }

  override def handleUserJoinWeb2x(msg: NewUserPresence2x): Unit = {
    RegisteredUsers2x.findWithToken(msg.token, state.registeredUsers.toVector) match {
      case Some(u) =>
        super.handleUserJoinWeb2x(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
    }
  }
}