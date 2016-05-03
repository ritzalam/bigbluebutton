package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api.{ EjectUserFromMeeting, UserJoining }
import org.bigbluebutton.core.domain.CanEjectUser
import org.bigbluebutton.core.handlers.{ UsersHandler, UsersHandler2x }
import org.bigbluebutton.core.models.{ Meeting2x, Users3x }

trait UsersHandlerFilter extends UsersHandler2x {
  val meeting: Meeting2x

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val permissionFilter = DefaultAbilitiesFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    Users3x.findWithId(msg.userId, meeting.users3x.toVector) foreach { user =>
      val abilities = permissionFilter.calcEffectiveAbilities(user.roles, user.permissions, meeting.getPermissions)
      if (permissionFilter.can(CanEjectUser, abilities)) {
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
}