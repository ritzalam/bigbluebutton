package org.bigbluebutton.core2x.models

import org.bigbluebutton.core2x.domain.{ RegisteredUser2x, _ }

object RegisteredUsers2x {
  def create(userId: IntUserId, extId: ExtUserId, name: Name, roles: Set[Role2x],
    token: AuthToken, avatar: Avatar,
    logoutUrl: LogoutUrl,
    welcome: Welcome,
    dialNumbers: Set[DialNumber],
    pinNumber: PinNumber,
    config: Set[String],
    extData: Set[String]): RegisteredUser2x = {
    new RegisteredUser2x(userId, extId, name, roles, token, avatar: Avatar,
      logoutUrl: LogoutUrl,
      welcome: Welcome,
      dialNumbers: Set[DialNumber],
      pinNumber: PinNumber,
      config: Set[String],
      extData: Set[String])
  }

  def findWithToken(token: AuthToken, users: Vector[RegisteredUser2x]): Option[RegisteredUser2x] = {
    users.find(u => u.authToken.value == token.value)
  }

  def findWithUserId(id: IntUserId, users: Vector[RegisteredUser2x]): Option[RegisteredUser2x] = {
    users.find(ru => id.value == ru.id.value)
  }
}

class RegisteredUsers2x {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser2x]

  def toVector: Vector[RegisteredUser2x] = regUsers.values.toVector

  def add(user: RegisteredUser2x): Vector[RegisteredUser2x] = {
    regUsers += user.authToken.value -> user
    regUsers.values.toVector
  }

  def remove(id: IntUserId): Option[RegisteredUser2x] = {
    val ru = RegisteredUsers2x.findWithUserId(id, toVector)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }
}