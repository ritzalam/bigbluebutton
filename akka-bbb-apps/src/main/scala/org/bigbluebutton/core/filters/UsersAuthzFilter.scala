package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api._
import org.bigbluebutton.core.domain.CanEjectUser
import org.bigbluebutton.core.handlers.{ UsersHandler, UsersHandler2x }
import org.bigbluebutton.core.models.{ Meeting2x, Users3x }

trait UsersHandlerFilter extends UsersHandler2x {
  val meeting: Meeting2x

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val abilitiesFilter = DefaultAbilitiesFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting): Unit = {
    meeting.state.users.findWithId(msg.userId) foreach { user =>
      val abilities = abilitiesFilter.calcEffectiveAbilities(user.roles, user.permissions, meeting.getPermissions)
      if (abilitiesFilter.can(CanEjectUser, abilities)) {
        // forward message to handler to process
        super.handleEjectUserFromMeeting(msg)
      } else {
        //     send request rejected message
      }
    }

  }

  abstract override def handleUserJoin(msg: UserJoining): Unit = {
    super.handleUserJoin(msg)
  }

  abstract override def handleUserJoinedVoiceConfListenOnly(msg: UserJoinedVoiceConf): Unit = {

  }
}