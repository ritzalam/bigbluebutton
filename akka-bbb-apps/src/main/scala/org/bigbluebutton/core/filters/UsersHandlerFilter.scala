package org.bigbluebutton.core.filters

import org.bigbluebutton.core.api.{ UserJoining, EjectUserFromMeeting }
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.handlers.UsersHandler

object Authz {
  val RAISE_HAND = "CanUserRaiseHand"
  val EJECT_USER = "CanUserEjectUser"
}

trait DefaultAuthz {
  def can(action: String, userAuthz: Set[String]): Boolean = {
    userAuthz contains action
  }
}

trait UsersHandlerFilter extends UsersHandler with DefaultAuthz {
  this: LiveMeeting =>

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    for {
      user <- usersModel.getUser(msg.userId)
      // if user can ejectUser {
      //     // forward message to handler to process
      //     super.handleEjectUserFromMeeting(msg)
      // } else {
      //     send request rejected message
      // }
    } super.handleEjectUserFromMeeting(msg)
  }

  abstract override def handleUserJoin(msg: UserJoining): Unit = {
    log.debug("AuthorizationFilter: Received user joined meeting. metingId=" + mProps.id + " userId=" + msg.userID)
    super.handleUserJoin(msg)
  }
}