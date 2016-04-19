package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api.{ EjectUserFromMeeting, UserJoining }
import org.bigbluebutton.core.domain.CanEjectUser
import org.bigbluebutton.core.handlers.{ UsersHandler, UsersHandler2x }
import org.bigbluebutton.core.models.{ Meeting2x, Users2x }

trait UsersHandlerFilter extends UsersHandler2x {
  val meeting: Meeting2x

  object DefaultPermissionsFilter extends DefaultPermissionsFilter
  val permFilter = DefaultPermissionsFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    Users2x.findWithId(msg.userId, meeting.users2x.toVector) foreach { user =>
      if (permFilter.can(CanEjectUser, user.permissions)) {
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