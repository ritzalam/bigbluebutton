package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api.UserJoining
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.handlers.UsersHandler

object Authz {
  val NAME = "name"
}

trait DefaultAuthz {
  def can(action: String, userAuthz: Set[String]): Boolean = {
    return true
  }
}

trait AuthorizationFilter extends UsersHandler {
  this: LiveMeeting =>

  abstract override def handleUserJoin(msg: UserJoining): Unit = {
    log.debug("AuthorizationFilter: Received user joined meeting. metingId=" + mProps.meetingID + " userId=" + msg.userID)
    super.handleUserJoin(msg)
  }
}