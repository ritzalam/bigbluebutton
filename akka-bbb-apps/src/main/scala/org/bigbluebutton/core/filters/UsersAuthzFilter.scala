package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api.{ EjectUserFromMeeting, UserJoining }
import org.bigbluebutton.core.domain.CanEjectUser
import org.bigbluebutton.core.handlers.{ UsersHandler, UsersHandler2x }
import org.bigbluebutton.core.models.{ Meeting2x, Users2x }

trait UsersHandlerFilter extends UsersHandler2x {
  val meeting: Meeting2x

  object DefaultPermissionsFilter extends DefaultPermissionsFilter
  val permissionFilter = DefaultPermissionsFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    Users2x.findWithId(msg.userId, meeting.users2x.toVector) foreach { user =>
      if (permissionFilter.can(CanEjectUser, user.permissions.permissions)) {
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