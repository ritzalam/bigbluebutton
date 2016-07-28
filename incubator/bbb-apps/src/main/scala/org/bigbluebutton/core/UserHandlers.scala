package org.bigbluebutton.core

import org.bigbluebutton.core.domain.{ IntUserId, RegisteredUser }
import org.bigbluebutton.core.meeting.handlers.user.UserActorMsgHdlr

class UserHandlers {
  private var userHandlers = new collection.immutable.HashMap[String, UserActorMsgHdlr]

  def createHandler(regUser: RegisteredUser, outGW: OutMessageGateway): UserActorMsgHdlr = {
    val userHandler = new UserActorMsgHdlr(regUser, outGW)
    userHandlers += regUser.id.value -> userHandler
    userHandler
  }

  def get(userId: IntUserId): Option[UserActorMsgHdlr] = {
    userHandlers.get(userId.value)
  }

  def remove(userId: IntUserId): Option[UserActorMsgHdlr] = {
    val handler = userHandlers.get(userId.value)
    handler foreach (h => userHandlers -= userId.value)
    handler
  }
}
