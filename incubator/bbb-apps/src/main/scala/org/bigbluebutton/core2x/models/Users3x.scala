package org.bigbluebutton.core2x.models

import org.bigbluebutton.core2x.domain._

object Users3x {
  def findWithId(id: IntUserId, users: Vector[User3x]): Option[User3x] = users.find(u => u.id.value == id.value)
  def findWithExtId(id: ExtUserId, users: Vector[User3x]): Option[User3x] = users.find(u => u.externalId.value == id.value)
  def findModerators(users: Vector[User3x]): Vector[User3x] = users.filter(u => u.roles.contains(ModeratorRole))
  def findPresenters(users: Vector[User3x]): Vector[User3x] = users.filter(u => u.roles.contains(PresenterRole))
  def hasModerator(users: Vector[User3x]): Boolean = users.filter(u => u.roles.contains(ModeratorRole)).length > 0
  def hasPresenter(users: Vector[User3x]): Boolean = users.filter(u => u.roles.contains(PresenterRole)).length > 0
}

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
}

