package org.bigbluebutton.core2x.models

import org.bigbluebutton.core2x.domain._

object UsersModel {
  def findWithId(id: IntUserId, users: Vector[User]): Option[User] = users.find(u => u.id.value == id.value)
  def findWithExtId(id: ExtUserId, users: Vector[User]): Option[User] = users.find(u => u.externalId.value == id.value)
  def findModerators(users: Vector[User]): Vector[User] = users.filter(u => u.roles.contains(ModeratorRole))
  def findPresenters(users: Vector[User]): Vector[User] = users.filter(u => u.roles.contains(PresenterRole))
  def hasModerator(users: Vector[User]): Boolean = users.filter(u => u.roles.contains(ModeratorRole)).length > 0
  def hasPresenter(users: Vector[User]): Boolean = users.filter(u => u.roles.contains(PresenterRole)).length > 0
}

class UsersModel {
  private var tokens: collection.immutable.HashMap[String, IntUserId] = new collection.immutable.HashMap[String, IntUserId]
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
}

