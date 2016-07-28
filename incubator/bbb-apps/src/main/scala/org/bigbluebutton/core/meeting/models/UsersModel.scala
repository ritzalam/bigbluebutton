package org.bigbluebutton.core.meeting.models

import org.bigbluebutton.core.domain._

object UsersModel {
  def findWithId(id: IntUserId, users: Vector[User]): Option[User] = users.find(u => u.id.value == id.value)
  def findWithExtId(id: ExtUserId, users: Vector[User]): Option[User] = users.find(u => u.externalId.value == id.value)
  def findModerators(users: Vector[User]): Vector[User] = users.filter(u => u.roles.contains(ModeratorRole))
  def findPresenters(users: Vector[User]): Vector[User] = users.filter(u => u.roles.contains(PresenterRole))
  def hasModerator(users: Vector[User]): Boolean = users.filter(u => u.roles.contains(ModeratorRole)).length > 0
  def hasPresenter(users: Vector[User]): Boolean = users.filter(u => u.roles.contains(PresenterRole)).length > 0

}

class UsersModel {
  private var tokens: collection.immutable.HashMap[SessionToken, IntUserId] = new collection.immutable.HashMap[SessionToken, IntUserId]
  private var users: collection.immutable.HashMap[String, User] = new collection.immutable.HashMap[String, User]

  def save(user: User): Unit = {
    users += user.id.value -> user
  }

  def remove(id: IntUserId): Option[User] = {
    val user = users.get(id.value)
    user foreach (u => users -= id.value)
    user
  }

  def toVector: Vector[User] = users.values.toVector

  def add(sessionToken: SessionToken, userId: IntUserId): Unit = {
    tokens += sessionToken -> userId
  }

  def removeSessionToken(sessionToken: SessionToken): Option[IntUserId] = {
    tokens.get(sessionToken) match {
      case Some(userId) =>
        tokens -= sessionToken
        Some(userId)
      case None => None
    }
  }

  def findUserWithToken(token: SessionToken): Option[User] = {
    for {
      userId <- tokens.get(token)
      user <- UsersModel.findWithId(userId, toVector)
    } yield user
  }
}

