package org.bigbluebutton.core.models

import org.bigbluebutton.core.domain.{ ModeratorRole, Role2x, UserAbilities, _ }

class Users3x {
  private var users: collection.immutable.HashMap[String, User3x] = new collection.immutable.HashMap[String, User3x]

  def save(user: User3x): Unit = {
    users += user.id.value -> user
  }

  def remove(id: IntUserId): Option[User3x] = {
    val user = users.get(id.value)
    user foreach (u => users -= id.value)
    user
  }

  def toVector: Vector[User3x] = users.values.toVector

  //  def findWithSessionId(sessionId: String, users: Vector[User3x]): Option[User3x] = users.find {
  //    u => u.sessionId.value == sessionId
  //  }

  def findWithId(id: IntUserId): Option[User3x] = users.values.find(u => u.id.value == id.value)

  //  def findWithExtId(id: ExtUserId, users: Vector[User3x]): Option[User3x] = users.find {
  //    u => u.extId.value == id.value
  //  }

  def findModerator(users: Vector[User3x]): Option[User3x] = users.find {
    u => u.roles.contains(ModeratorRole)
  }

}

object Users3x {
  def create(id: IntUserId, extId: ExtUserId, name: Name, roles: Set[Role2x]): User3x = {

    new User3x(id, extId, name, EmojiStatus("none"), roles,
      Set.empty, new UserAbilities(Set.empty, Set.empty, false),
      Set.empty, Set.empty, Set.empty)
  }
}

object RegisteredUsers2x {
  def apply(): RegisteredUsers2x = new RegisteredUsers2x()

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

}

class RegisteredUsers2x {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser2x]

  def toVector: Vector[RegisteredUser2x] = regUsers.values.toVector

  def add(user: RegisteredUser2x): Vector[RegisteredUser2x] = {
    regUsers += user.authToken.value -> user
    regUsers.values.toVector
  }

  def findWithToken(token: AuthToken): Option[RegisteredUser2x] = {
    regUsers.get(token.value)
  }

  def findWithUserId(id: IntUserId): Option[RegisteredUser2x] = {
    regUsers.values find (ru => id.value == ru.id.value)
  }

  def remove(id: IntUserId): Option[RegisteredUser2x] = {
    val ru = regUsers.get(id.value)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }
}

object Users4x {
  def add(user: User3x, users: Vector[User3x]): Vector[User3x] = {
    users :+ user
  }
}

case class Users4x(users: Vector[User3x])

object RegisteredUsers4x {
  def add(user: RegisteredUser2x, users: Vector[RegisteredUser2x]): Vector[RegisteredUser2x] = {
    users :+ user
  }

  def findWithToken(token: AuthToken, users: Vector[RegisteredUser2x]): Option[RegisteredUser2x] = {
    users find (ru => ru.authToken.value == token.value)
  }

  def findWithUserId(id: IntUserId, users: Vector[RegisteredUser2x]): Option[RegisteredUser2x] = {
    users find (ru => id.value == ru.id.value)
  }

  def remove(id: IntUserId, users: Vector[RegisteredUser2x]): Vector[RegisteredUser2x] = {
    users filterNot (u => u.id.value == id.value)
  }
}

case class RegisteredUsers4x(registeredUsers: Vector[RegisteredUser2x])
