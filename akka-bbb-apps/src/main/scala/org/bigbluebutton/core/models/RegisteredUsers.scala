package org.bigbluebutton.core.models

case class RegisteredUser2(id: String, externId: String, name: String, roles: Set[String], authToken: String)

class RegisteredUsers {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser2]

  def add(token: String, regUser: RegisteredUser2) {
    regUsers += token -> regUser
  }

  def findWithToken(token: String): Option[RegisteredUser2] = {
    regUsers.get(token)
  }

  def findWithUserId(userID: String): Option[RegisteredUser2] = {
    regUsers.values find (ru => userID contains ru.id)
  }

  def remove(userId: String): Option[RegisteredUser2] = {
    val ru = findWithUserId(userId)
    ru foreach { u => regUsers -= u.authToken }
    ru
  }
}