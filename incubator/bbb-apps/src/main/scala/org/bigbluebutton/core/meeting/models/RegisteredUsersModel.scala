package org.bigbluebutton.core.meeting.models

import org.bigbluebutton.core.domain._

object RegisteredUsersModel {
  def create(userId: IntUserId, extId: ExtUserId, name: Name, roles: Set[Role],
    token: SessionToken, avatar: Avatar,
    logoutUrl: LogoutUrl,
    welcome: Welcome,
    dialNumbers: Set[DialNumber],
    pinNumber: PinNumber,
    config: String,
    extData: String): RegisteredUser = {
    new RegisteredUser(userId, extId, name, roles, token, avatar: Avatar,
      logoutUrl: LogoutUrl,
      welcome: Welcome,
      dialNumbers: Set[DialNumber],
      pinNumber: PinNumber,
      config: String,
      extData: String)
  }

  def findWithToken(token: SessionToken, users: Vector[RegisteredUser]): Option[RegisteredUser] = {
    users.find(u => u.authToken.value == token.value)
  }

  def findWithUserId(id: IntUserId, users: Vector[RegisteredUser]): Option[RegisteredUser] = {
    users.find(ru => id.value == ru.id.value)
  }
}

class RegisteredUsersModel {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser]

  def toVector: Vector[RegisteredUser] = regUsers.values.toVector

  def add(user: RegisteredUser): Vector[RegisteredUser] = {
    regUsers += user.authToken.value -> user
    regUsers.values.toVector
  }

  def remove(id: IntUserId): Option[RegisteredUser] = {
    val ru = RegisteredUsersModel.findWithUserId(id, toVector)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }
}