package org.bigbluebutton.core.models

import org.bigbluebutton.core.util.RandomStringGenerator

object Role extends Enumeration {
  type Role = Value
  val MODERATOR = Value("MODERATOR")
  val VIEWER = Value("VIEWER")
}

case class RegisteredUser(id: String, externId: String, name: String, role: Role.Role, authToken: String)

case class UserVO(userID: String, externUserID: String, name: String, role: Role.Role,
  emojiStatus: String, presenter: Boolean, hasStream: Boolean, locked: Boolean,
  webcamStreams: Set[String], phoneUser: Boolean, voiceUser: VoiceUser,
  listenOnly: Boolean, joinedWeb: Boolean)

case class VoiceUser(userId: String, webUserId: String, callerName: String,
  callerNum: String, joined: Boolean, locked: Boolean, muted: Boolean,
  talking: Boolean, listenOnly: Boolean)

case class User2(id: String, extId: String, name: String, roles: Set[String],
  emojiStatus: String, presenter: Boolean, hasStream: Boolean, locked: Boolean,
  webcamStreams: Set[String], phoneUser: Boolean, voiceUser: VoiceUser2,
  listenOnly: Boolean, joinedWeb: Boolean)

case class VoiceUser2(id: String, webId: String, callerName: String,
  callerNum: String, joined: Boolean, locked: Boolean, muted: Boolean,
  talking: Boolean, listenOnly: Boolean)

class Users2 {
  var users = new collection.immutable.HashMap[String, User2]

  def add(uvo: User2) {
    users += uvo.id -> uvo
  }

  def remove(id: String): Option[User2] = {
    val user = users get (id)
    user foreach (u => users -= id)
    user
  }

  def generateWebUserId(): String = {
    val webUserId = RandomStringGenerator.randomAlphanumericString(6)
    if (!hasUser(webUserId)) webUserId else generateWebUserId
  }

  def hasUser(userId: String): Boolean = {
    users.contains(userId)
  }
}