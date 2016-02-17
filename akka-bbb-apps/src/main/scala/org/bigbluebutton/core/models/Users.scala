package org.bigbluebutton.core.models

import org.bigbluebutton.core.util.RandomStringGenerator

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

  def remove(id: String): Option[UserVO] = {
    val user = uservos get (userId)
    user foreach (u => uservos -= userId)
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