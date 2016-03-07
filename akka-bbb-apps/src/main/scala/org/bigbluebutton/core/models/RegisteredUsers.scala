package org.bigbluebutton.core.models

case class RegisteredUser(id: IntUserId, extId: ExtUserId, name: Name, role: Role.Role, authToken: AuthToken)

object RegisteredUsers {
  def create(userId: IntUserId, extId: ExtUserId, name: Name, role: Role.Role, token: AuthToken): Option[RegisteredUser] = {
    Some(new RegisteredUser(userId, extId, name, role, token))
  }

}

class RegisteredUsers {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser]

  def toArray: Array[RegisteredUser] = regUsers.values.toArray

  def add(token: AuthToken, regUser: RegisteredUser): Array[RegisteredUser] = {
    regUsers += token.value -> regUser
    regUsers.values.toArray
  }

  def findWithToken(token: AuthToken): Option[RegisteredUser] = {
    regUsers.get(token.value)
  }

  def findWithUserId(userId: IntUserId): Option[RegisteredUser] = {
    regUsers.values find (ru => userId.value contains ru.id.value)
  }

  def remove(userId: IntUserId): Option[RegisteredUser] = {
    val ru = findWithUserId(userId)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }
}