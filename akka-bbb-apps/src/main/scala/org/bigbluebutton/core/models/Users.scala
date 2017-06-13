package org.bigbluebutton.core.models

import org.bigbluebutton.core.util.RandomStringGenerator
import com.softwaremill.quicklens._

import scala.collection.immutable.ListSet

object Roles {
  val MODERATOR_ROLE = "MODERATOR"
  val PRESENTER_ROLE = "PRESENTER"
  val VIEWER_ROLE = "VIEWER"
  val GUEST_ROLE = "GUEST"
}

object Users {

  def newUser(userId: String, lockStatus: Boolean, ru: RegisteredUser, waitingForAcceptance: Boolean,
    vu: VoiceUser, users: Users): Option[UserVO] = {
    val uvo = new UserVO(userId, ru.externId, ru.name,
      ru.role, ru.guest, ru.authed, waitingForAcceptance = waitingForAcceptance, emojiStatus = "none", presenter = false,
      hasStream = false, locked = lockStatus,
      webcamStreams = new ListSet[String](), phoneUser = false, vu,
      listenOnly = vu.listenOnly, avatarURL = vu.avatarURL, joinedWeb = true)
    users.save(uvo)
    Some(uvo)
  }

  def findWithIntId(intId: String, users: Users): Option[UserState] = {
    users.toVector.find(u => u.intId == intId)
  }

  def findWithExtId(extId: String, users: Users): Option[UserState] = {
    users.toVector.find(u => u.extId == extId)
  }

  def findModerators(users: Users): Vector[UserState] = {
    users.toVector.filter(u => u.role == Roles.MODERATOR_ROLE)
  }

  def findPresenters(users: Users): Vector[UserState] = {
    users.toVector.filter(u => u.presenter)
  }

  def findViewers(users: Users): Vector[UserState] = {
    users.toVector.filter(u => u.role == Roles.VIEWER_ROLE)
  }

  def hasModerator(users: Users): Boolean = {
    findModerators(users).length > 0
  }

  def hasPresenter(users: Users): Boolean = {
    findPresenters(users).length > 0
  }

  def numUsers(users: Users): Int = {
    users.toVector.length
  }

  def numWebUsers(users: Users): Int = {
    users.toVector.length
  }

  def numUsersInVoiceConference(users: Users): Int = {
    VoiceUsers.numUsers(users.voiceUsers)
  }

  def getUserWithVoiceUserId(voiceUserId: String, users: Users): Option[VoiceUserState] = {
    VoiceUsers.findWithVoiceUserId(users.voiceUsers, voiceUserId)
  }

  def numModerators(users: Users): Int = findModerators(users).length

  def findAModerator(users: Users): Option[UserState] = {
    val mods = findModerators(users)
    if (mods.length > 1) Some(mods.head) else None
  }

  def getCurrentPresenter(users: Users): Option[UserState] = {
    users.toVector.find(u => u.presenter)
  }

  def getUsers(users: Users): Vector[UserState] = users.toVector

  def userLeft(userId: String, users: Users): Option[UserState] = {
    users.remove(userId)
  }

  def unbecomePresenter(intId: String, users: Users) = {
    for {
      u <- findWithIntId(intId, users)
      user = modify(u)(_.presenter).setTo(false)
    } yield users.save(user)
  }

  def becomePresenter(intId: String, users: Users) = {
    for {
      u <- findWithIntId(intId, users)
      user = modify(u)(_.presenter).setTo(true)
    } yield users.save(user)
  }

  def isModerator(id: String, users: Users): Boolean = {
    findWithIntId(id, users) match {
      case Some(user) => user.role == Roles.MODERATOR_ROLE && !user.waitingForAcceptance
      case None => false
    }
  }

  def generateWebUserId(users: Users): String = {
    val webUserId = RandomStringGenerator.randomAlphanumericString(6)
    findWithIntId(webUserId, users) match {
      case Some(u) => webUserId
      case None => generateWebUserId(users)
    }
  }

  def usersWhoAreNotPresenter(users: Users): Vector[UserState] = {
    users.toVector filter (u => u.presenter == false)
  }

  def joinedVoiceListenOnly(userId: String, users: Users): Option[VoiceUserState] = {
    VoiceUsers.joinedVoiceListenOnly(users.voiceUsers, userId)
  }

  def leftVoiceListenOnly(userId: String, users: Users): Option[VoiceUserState] = {
    VoiceUsers.leftVoiceListenOnly(users.voiceUsers, userId)
  }

  def lockUser(userId: String, lock: Boolean, users: Users): Option[UserState] = {
    for {
      u <- findWithIntId(userId, users)
      uvo = u.modify(_.locked).setTo(lock) // u.copy(locked = msg.lock)
    } yield {
      users.save(uvo)
      uvo
    }
  }

  def changeRole(userId: String, users: Users, role: String): Option[UserState] = {
    for {
      u <- findWithIntId(userId, users)
      uvo = u.modify(_.role).setTo(role)
    } yield {
      users.save(uvo)
      uvo
    }
  }

  def userSharedWebcam(userId: String, users: Users, streamId: String): Option[VoiceUserState] = {
    for {
      u <- findWithIntId(userId, users)
      streams = u.webcamStreams + streamId
      uvo = u.modify(_.hasStream).setTo(true).modify(_.webcamStreams).setTo(streams)
    } yield {
      users.save(uvo)
      uvo
    }
  }

  def userUnsharedWebcam(userId: String, users: Users, streamId: String): Option[VoiceUserState] = {

    def findWebcamStream(streams: Set[String], stream: String): Option[String] = {
      streams find (w => w == stream)
    }

    for {
      u <- findWithIntId(userId, users)
      streamName <- findWebcamStream(u.webcamStreams, streamId)
      streams = u.webcamStreams - streamName
      uvo = u.modify(_.hasStream).setTo(!streams.isEmpty).modify(_.webcamStreams).setTo(streams)
    } yield {
      users.save(uvo)
      uvo
    }
  }

  def setEmojiStatus(userId: String, users: Users, emoji: String): Option[UserState] = {
    for {
      u <- findWithIntId(userId, users)
      uvo = u.modify(_.emoji).setTo(emoji)
    } yield {
      users.save(uvo)
      uvo
    }
  }

  def setWaitingForAcceptance(user: UserState, users: Users, waitingForAcceptance: Boolean): UserState = {
    val nu = user.modify(_.waitingForAcceptance).setTo(waitingForAcceptance)
    users.save(nu)
    nu
  }

  def setUserTalking(voiceUserId: String, users: Users, talking: Boolean): Option[VoiceUserState] = {
    VoiceUsers.setUserTalking(users.voiceUsers, voiceUserId, talking)
  }

  def setUserMuted(voiceUserId: String, users: Users, muted: Boolean): Option[VoiceUserState] = {
    VoiceUsers.setUserMuted(users.voiceUsers, voiceUserId, muted)
  }

  def resetVoiceUser(user: UserVO, users: Users): VoiceUserState = {
    val vu = new VoiceUser(user.id, user.id, user.name, user.name,
      joined = false, locked = false, muted = false, talking = false, user.avatarURL, listenOnly = false)

    val nu = user.modify(_.voiceUser).setTo(vu)
      .modify(_.phoneUser).setTo(false)
      .modify(_.listenOnly).setTo(false)
    users.save(nu)
    nu
  }

  def switchUserToPhoneUser(user: UserVO, users: Users, voiceUserId: String, userId: String,
    callerIdName: String, callerIdNum: String, muted: Boolean, talking: Boolean,
    avatarURL: String, listenOnly: Boolean): UserVO = {
    val vu = new VoiceUser(voiceUserId, userId, callerIdName,
      callerIdNum, joined = true, locked = false,
      muted, talking, avatarURL, listenOnly)
    val nu = user.modify(_.voiceUser).setTo(vu)
      .modify(_.listenOnly).setTo(listenOnly)
    users.save(nu)
    nu
  }

  def restoreMuteState(user: UserVO, users: Users, voiceUserId: String, userId: String,
    callerIdName: String, callerIdNum: String, muted: Boolean, talking: Boolean,
    avatarURL: String, listenOnly: Boolean): UserVO = {
    val vu = new VoiceUser(voiceUserId, userId, callerIdName,
      callerIdNum, joined = true, locked = false,
      muted, talking, avatarURL, listenOnly)
    val nu = user.modify(_.voiceUser).setTo(vu)
      .modify(_.listenOnly).setTo(listenOnly)
    users.save(nu)
    nu
  }

  def makeUserPhoneUser(vu: VoiceUser, users: Users, webUserId: String, externUserId: String,
    callerIdName: String, lockStatus: Boolean, listenOnly: Boolean, avatarURL: String): UserVO = {
    val uvo = new UserVO(webUserId, externUserId, callerIdName,
      Roles.VIEWER_ROLE, guest = false, authed = false, waitingForAcceptance = false, emojiStatus = "none", presenter = false,
      hasStream = false, locked = lockStatus,
      webcamStreams = new ListSet[String](),
      phoneUser = !listenOnly, vu, listenOnly = listenOnly, avatarURL = avatarURL, joinedWeb = false)

    users.save(uvo)
    uvo
  }

}

class Users {
  private var users: collection.immutable.HashMap[String, UserState] = new collection.immutable.HashMap[String, UserState]

  // Collection of users that left the meeting. We keep a cache of the old users state to recover in case
  // the user reconnected by refreshing the client. (ralam june 13, 2017)
  private var usersCache: collection.immutable.HashMap[String, UserState] = new collection.immutable.HashMap[String, UserState]

  private val voiceUsers = new VoiceUsers

  private def toVector: Vector[UserState] = users.values.toVector

  private def save(user: UserState): UserState = {
    users += user.intId -> user
    user
  }

  private def remove(id: String): Option[UserState] = {
    for {
      user <- users.get(id)
    } yield {
      users -= id
      saveToCache(user)
      user
    }
  }

  private def saveToCache(user: UserState): Unit = {
    usersCache += user.intId -> user
  }

  private def removeFromCache(intId: String): Option[UserState] = {
    for {
      user <- usersCache.get(intId)
    } yield {
      usersCache -= intId
      user
    }
  }

  private def findUserFromCache(intId: String): Option[UserState] = {
    usersCache.values.find(u => u.intId == intId)
  }

}

case class UserIdAndName(id: String, name: String)

case class UserVO(id: String, externalId: String, name: String, role: String,
  guest: Boolean, authed: Boolean, waitingForAcceptance: Boolean, emojiStatus: String,
  presenter: Boolean, hasStream: Boolean, locked: Boolean, webcamStreams: Set[String],
  phoneUser: Boolean, voiceUser: VoiceUser, listenOnly: Boolean, avatarURL: String,
  joinedWeb: Boolean)

case class VoiceUser(userId: String, webUserId: String, callerName: String,
  callerNum: String, joined: Boolean, locked: Boolean, muted: Boolean,
  talking: Boolean, avatarURL: String, listenOnly: Boolean)
