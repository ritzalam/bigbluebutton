package org.bigbluebutton.core.models

import org.bigbluebutton.core.domain._

import scala.collection.immutable.HashMap

object UsersUtil {
  def muteUser(user: User2x): Boolean = {
    true
  }
}

object Users2x {

  def apply(): Users2x = new Users2x()

  def create(
    id: IntUserId,
    extId: ExtUserId,
    name: Name,
    sessionId: SessionId,
    roles: Set[Role2x],
    presence: Set[Presence],
    permissions: UserPermissions): User2x = {

    new User2x(id, extId, name, sessionId, roles, presence, permissions, Set.empty, Set.empty)
  }

  def findWithSessionId(sessionId: String, users: Vector[User2x]): Option[User2x] = users.find {
    u => u.sessionId.value == sessionId
  }

  def findWithId(id: IntUserId, users: Vector[User2x]): Option[User2x] = users.find {
    u => u.id.value == id.value
  }

  def findWithExtId(id: ExtUserId, users: Vector[User2x]): Option[User2x] = users.find {
    u => u.extId.value == id.value
  }

  def findModerator(users: Vector[User2x]): Option[User2x] = users.find {
    u => u.roles.contains(ModeratorRole)
  }
}

class Users2x {
  private var users = new HashMap[String, User2x]

  def save(user: User2x): Vector[User2x] = {
    users += user.id.value -> user
    users.values.toVector
  }

  def remove(id: IntUserId): Option[User2x] = {
    val user = users.get(id.value)
    user foreach (u => users -= id.value)
    user
  }

  def toVector: Vector[User2x] = users.values.toVector
}

object RegisteredUsers2x {
  def apply(): RegisteredUsers2x = new RegisteredUsers2x()

  def create(userId: IntUserId,
    extId: ExtUserId,
    name: Name,
    roles: Set[Role2x],
    token: AuthToken): RegisteredUser2x = {
    new RegisteredUser2x(userId, extId, name, roles, token)
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