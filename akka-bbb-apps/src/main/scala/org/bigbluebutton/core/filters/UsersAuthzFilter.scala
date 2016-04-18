package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api.{ EjectUserFromMeeting, UserJoining }
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.domain.CanEjectUser
import org.bigbluebutton.core.handlers.UsersHandler
import org.bigbluebutton.core.models.Users2x

trait UsersHandlerFilter extends UsersHandler with DefaultPermissionsFilter {
  this: LiveMeeting =>

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    Users2x.findWithId(msg.userId, meeting.users2x.toVector) foreach { user =>
      if (can(CanEjectUser, user.permissions)) {
        // forward message to handler to process
        super.handleEjectUserFromMeeting(msg)
      } else {
        //     send request rejected message
      }
    }

  }

  abstract override def handleUserJoin(msg: UserJoining): Unit = {
    log.debug("AuthorizationFilter: Received user joined meeting. metingId=" + props.id + " userId=" + msg.userId)
    super.handleUserJoin(msg)
  }
}