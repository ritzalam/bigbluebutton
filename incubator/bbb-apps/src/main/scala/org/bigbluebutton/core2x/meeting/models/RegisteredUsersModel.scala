package org.bigbluebutton.core2x.meeting.models

import org.bigbluebutton.core2x.domain.{ RegisteredUser2x, _ }

object RegisteredUsersModel {
  def create(userId: IntUserId, extId: ExtUserId, name: Name, roles: Set[Role2x],
    token: SessionToken, avatar: Avatar,
    logoutUrl: LogoutUrl,
    welcome: Welcome,
    dialNumbers: Set[DialNumber],
    pinNumber: PinNumber,
    config: String,
    extData: String): RegisteredUser2x = {
    new RegisteredUser2x(userId, extId, name, roles, token, avatar: Avatar,
      logoutUrl: LogoutUrl,
      welcome: Welcome,
      dialNumbers: Set[DialNumber],
      pinNumber: PinNumber,
      config: String,
      extData: String)
  }

  def findWithToken(token: SessionToken, users: Vector[RegisteredUser2x]): Option[RegisteredUser2x] = {
    users.find(u => u.authToken.value == token.value)
  }

  def findWithUserId(id: IntUserId, users: Vector[RegisteredUser2x]): Option[RegisteredUser2x] = {
    users.find(ru => id.value == ru.id.value)
  }
}

class RegisteredUsersModel {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser2x]

  def toVector: Vector[RegisteredUser2x] = regUsers.values.toVector

  def add(user: RegisteredUser2x): Vector[RegisteredUser2x] = {
    regUsers += user.authToken.value -> user
    regUsers.values.toVector
  }

  def remove(id: IntUserId): Option[RegisteredUser2x] = {
    val ru = RegisteredUsersModel.findWithUserId(id, toVector)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }
}