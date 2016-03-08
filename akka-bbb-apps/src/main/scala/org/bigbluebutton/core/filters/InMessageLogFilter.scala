package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api.{ UserJoining, EjectUserFromMeeting }
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.handlers.UsersHandler

trait InMessageLogFilter extends UsersHandler {
  this: LiveMeeting =>

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    for {
      user <- meeting.getUser(msg.userId)
      // if user can ejectUser {
      //     // forward message to handler to process
      //     super.handleEjectUserFromMeeting(msg)
      // } else {
      //     send request rejected message
      // }
    } super.handleEjectUserFromMeeting(msg)
  }

  abstract override def handleUserJoin(msg: UserJoining): Unit = {
    log.debug("AuthorizationFilter: Received user joined meeting. metingId=" + props.id + " userId=" + msg.userId)
    super.handleUserJoin(msg)
  }
}