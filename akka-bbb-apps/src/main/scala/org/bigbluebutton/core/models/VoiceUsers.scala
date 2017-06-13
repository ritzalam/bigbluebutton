package org.bigbluebutton.core.models

import com.softwaremill.quicklens._

object VoiceUsers {
  def findWithIntId(users: VoiceUsers, intId: String): Option[VoiceUserState] = {
    users.toVector.find(u => u.intId == intId)
  }

  def findWithVoiceUserId(users: VoiceUsers, voiceUserId: String): Option[VoiceUserState] = {
    users.toVector find (u => u.voiceUserId == voiceUserId)
  }

  def add(users: VoiceUsers, user: VoiceUserState): Option[VoiceUserState] = {
    users.save(user)
    Some(user)
  }

  def remove(users: VoiceUsers, intId: String): Option[VoiceUserState] = {
    users.remove(intId)
  }

  def removeUserWithId(users: VoiceUsers, voiceUserId: String): Option[VoiceUserState] = {
    users.remove(voiceUserId)
  }

  def numUsers(users: VoiceUsers): Int = {
    users.toVector.length
  }

  def joinedVoiceListenOnly(users: VoiceUsers, userId: String): Option[VoiceUserState] = {
    for {
      u <- findWithIntId(users, userId)
      vu = u.modify(_.talking).setTo(false)
        .modify(_.listenOnly).setTo(true)
    } yield {
      users.save(vu)
      vu
    }
  }

  def leftVoiceListenOnly(users: VoiceUsers, userId: String): Option[VoiceUserState] = {
    for {
      u <- findWithIntId(users, userId)
      vu = u.modify(_.talking).setTo(false)
        .modify(_.listenOnly).setTo(false)
    } yield {
      users.save(vu)
      vu
    }
  }

  def setUserTalking(users: VoiceUsers, userId: String, talking: Boolean): Option[VoiceUserState] = {
    for {
      u <- findWithVoiceUserId(users, userId)
    } yield {
      val nv = u.modify(_.talking).setTo(talking)
      users.save(nv)
      nv
    }
  }

  def setUserMuted(users: VoiceUsers, userId: String, muted: Boolean): Option[VoiceUserState] = {
    for {
      user <- findWithVoiceUserId(users, userId)
    } yield {
      val talking: Boolean = if (muted) false else user.talking
      val nv = user.modify(_.muted).setTo(muted).modify(_.talking).setTo(talking)
      users.save(nv)
      nv
    }

  }
}

class VoiceUsers {
  private var users: collection.immutable.HashMap[String, VoiceUserState] = new collection.immutable.HashMap[String, VoiceUserState]

  // Collection of users that left the meeting. We keep a cache of the old users state to recover in case
  // the user reconnected by refreshing the client. (ralam june 13, 2017)
  private var usersCache: collection.immutable.HashMap[String, VoiceUserState] = new collection.immutable.HashMap[String, VoiceUserState]

  private def toVector: Vector[VoiceUserState] = users.values.toVector

  private def save(user: VoiceUserState): VoiceUserState = {
    users += user.intId -> user
    user
  }

  private def remove(id: String): Option[VoiceUserState] = {
    for {
      user <- users.get(id)
    } yield {
      users -= id
      saveToCache(user)
      user
    }
  }

  private def saveToCache(user: VoiceUserState): Unit = {
    usersCache += user.intId -> user
  }

  private def removeFromCache(intId: String): Option[VoiceUserState] = {
    for {
      user <- usersCache.get(intId)
    } yield {
      usersCache -= intId
      user
    }
  }

  private def findUserFromCache(intId: String): Option[VoiceUserState] = {
    usersCache.values.find(u => u.intId == intId)
  }
}

case class VoiceUser2x(intId: String, voiceUserId: String)
case class VoiceUserVO2x(intId: String, voiceUserId: String, callerName: String,
  callerNum: String, joined: Boolean, locked: Boolean, muted: Boolean,
  talking: Boolean, callingWith: String, listenOnly: Boolean)