package org.bigbluebutton.core

import org.bigbluebutton.core.domain.{ IntUserId, RegisteredUser }
import org.bigbluebutton.core.meeting.handlers.user.UserActorMessageHandler

class UserHandlers {
  private var userHandlers = new collection.immutable.HashMap[String, UserActorMessageHandler]

  def createHandler(regUser: RegisteredUser, outGW: OutMessageGateway): UserActorMessageHandler = {
    val userHandler = new UserActorMessageHandler(regUser, outGW)
    userHandlers += regUser.id.value -> userHandler
    userHandler
  }

  def get(userId: IntUserId): Option[UserActorMessageHandler] = {
    userHandlers.get(userId.value)
  }

  def remove(userId: IntUserId): Option[UserActorMessageHandler] = {
    val handler = userHandlers.get(userId.value)
    handler foreach (h => userHandlers -= userId.value)
    handler
  }
}
