package org.bigbluebutton.core

import org.bigbluebutton.core.user.handlers.UserInMsgHdlr
import org.bigbluebutton.core.domain.{ IntUserId, RegisteredUser }

class UserHandlers {
  private var userHandlers = new collection.immutable.HashMap[String, UserInMsgHdlr]

  def createHandler(regUser: RegisteredUser, outGW: OutMessageGateway): UserInMsgHdlr = {
    val userHandler = new UserInMsgHdlr(regUser, outGW)
    userHandlers += regUser.id.value -> userHandler
    userHandler
  }

  def get(userId: IntUserId): Option[UserInMsgHdlr] = {
    userHandlers.get(userId.value)
  }

  def remove(userId: IntUserId): Option[UserInMsgHdlr] = {
    val handler = userHandlers.get(userId.value)
    handler foreach (h => userHandlers -= userId.value)
    handler
  }
}
