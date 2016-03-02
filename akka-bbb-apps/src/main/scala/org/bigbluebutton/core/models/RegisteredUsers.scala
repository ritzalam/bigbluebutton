package org.bigbluebutton.core.models

case class RegisteredUser(id: IntUserId, extId: ExtUserId, name: Name, role: Role.Role, authToken: AuthToken)

object RegisteredUsers {
  def create(userId: IntUserId, extId: ExtUserId, name: Name, role: Role.Role, token: AuthToken): Option[RegisteredUser] = {
    Some(new RegisteredUser(userId, extId, name, role, token))
  }
}

class RegisteredUsers {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser]

  def add(token: AuthToken, regUser: RegisteredUser): Option[RegisteredUser] = {
    regUsers += token.value -> regUser

    Some(regUser)
  }

  def findWithToken(token: AuthToken): Option[RegisteredUser] = {
    regUsers.get(token.value)
  }

  def findWithUserId(userId: IntUserId): Option[RegisteredUser] = {
    regUsers.values find (ru => userId.value contains ru.id)
  }

  def remove(userId: IntUserId): Option[RegisteredUser] = {
    val ru = findWithUserId(userId)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }

  def addRegisteredUser(token: String, regUser: RegisteredUser) {
    regUsers += token -> regUser
  }

  def getRegisteredUserWithToken(token: AuthToken): Option[RegisteredUser] = {
    regUsers.get(token.value)
  }

  def getRegisteredUserWithUserID(userId: IntUserId): Option[RegisteredUser] = {
    regUsers.values find (ru => userId.value contains ru.id.value)
  }

  def removeRegUser(userId: IntUserId) {
    getRegisteredUserWithUserID(userId) match {
      case Some(ru) => {
        regUsers -= ru.authToken.value
      }
      case None =>
    }
  }
}