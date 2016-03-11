package org.bigbluebutton.core.models

import org.bigbluebutton.core.domain._

import scala.collection.immutable.HashMap

object RegisteredUsers2x {
  def apply(): RegisteredUsers2x = new RegisteredUsers2x()

  def create(userId: IntUserId, extId: ExtUserId, name: Name, roles: Set[String], token: AuthToken): RegisteredUser = {
    new RegisteredUser(userId, extId, name, roles, token)
  }

}

class RegisteredUsers2x {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser]

  def toArray: Array[RegisteredUser] = regUsers.values.toArray

  def add(user: RegisteredUser): Vector[RegisteredUser] = {
    regUsers += user.authToken.value -> user
    regUsers.values.toVector
  }

  def findWithToken(token: AuthToken): Option[RegisteredUser] = {
    regUsers.get(token.value)
  }

  def findWithUserId(id: IntUserId): Option[RegisteredUser] = {
    regUsers.values find (ru => id.value == ru.id.value)
  }

  def remove(id: IntUserId): Option[RegisteredUser] = {
    val ru = regUsers.get(id.value)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }
}

trait RegisteredUsers {
  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser]

  def createRegisteredUser(userId: IntUserId, extId: ExtUserId, name: Name, roles: Set[String], token: AuthToken): Option[RegisteredUser] = {
    Some(new RegisteredUser(userId, extId, name, roles, token))
  }

  def toArray: Array[RegisteredUser] = regUsers.values.toArray

  def addRegisteredUser(token: AuthToken, regUser: RegisteredUser): Array[RegisteredUser] = {
    regUsers += token.value -> regUser
    regUsers.values.toArray
  }

  def findWithToken(token: AuthToken): Option[RegisteredUser] = {
    regUsers.get(token.value)
  }

  def findWithUserId(userId: IntUserId): Option[RegisteredUser] = {
    regUsers.values find (ru => userId.value contains ru.id.value)
  }

  def removeRegisteredUser(userId: IntUserId): Option[RegisteredUser] = {
    val ru = findWithUserId(userId)
    ru foreach { u => regUsers -= u.authToken.value }
    ru
  }
}

object Users2x {

  def apply(): Users2x = new Users2x()

  def findWithSessionId(sessionId: String, users: Vector[User2x]): Option[User2x] = users.find {
    u => u.sessionId.value == sessionId
  }

  def findWithId(id: IntUserId, users: Vector[User2x]): Option[User2x] = users.find {
    u => u.id.value == id.value
  }

  def findWithExtId(id: ExtUserId, users: Vector[User2x]): Option[User2x] = users.find {
    u => u.extId.value == id.value
  }

  def findModerator(users: Vector[User2x]): Option[User2x] = users.find {
    u => u.roles.contains(Role.MODERATOR)
  }
}

class Users2x {
  private var users = new HashMap[String, User2x]

  def save(user: User2x): Vector[User2x] = {
    users += user.id.value -> user
    users.values.toVector
  }

  def remove(id: IntUserId): Option[User2x] = {
    val user = users.get(id.value)
    user foreach (u => users -= id.value)
    user
  }

  def toVector: Vector[User2x] = users.values.toVector
}

trait Users {
  private var users = new collection.immutable.HashMap[String, UserVO]

  def saveUser(uvo: UserVO): Array[UserVO] = {
    users += uvo.id.value -> uvo
    users.values.toArray
  }

  /* When reconnecting SIP global audio, users may receive the connection message
   * before the disconnection message.
   * This variable is a connection counter that should control this scenario.
   */
  private var globalAudioConnectionCounter = new collection.immutable.HashMap[String, Integer]

  private var locked = false
  private var meetingMuted = false
  private var recordingVoice = false

  private var currentPresenter = new Presenter(IntUserId("system"), Name("system"), IntUserId("system"))

  def setCurrentPresenterInfo(pres: Presenter) {
    currentPresenter = pres
  }

  def getCurrentPresenterInfo(): Presenter = {
    currentPresenter
  }

  def removeUser(userId: IntUserId): Option[UserVO] = {
    val user = users get (userId.value)
    user foreach (u => users -= userId.value)
    user
  }

  def hasSessionId(sessionId: String): Boolean = {
    users.contains(sessionId)
  }

  def hasUser(userId: IntUserId): Boolean = {
    users.contains(userId.value)
  }

  def numUsers(): Int = {
    users.size
  }

  def numWebUsers(): Int = {
    users.values filter (u => u.phoneUser.value == false) size
  }

  def numUsersInVoiceConference: Int = {
    val joinedUsers = users.values filter (u => u.voiceUser.joinedVoice.value)
    joinedUsers.size
  }

  def getUserWithExternalId(userID: String): Option[UserVO] = {
    users.values find (u => u.extId.value == userID)
  }

  def getUserWithVoiceUserId(voiceUserId: VoiceUserId): Option[UserVO] = {
    users.values find (u => u.voiceUser.id.value == voiceUserId.value)
  }

  def getUser(userId: IntUserId): Option[UserVO] = {
    users.values find (u => u.id.value == userId.value)
  }

  def getUsers(): Array[UserVO] = {
    users.values toArray
  }

  def numModerators(): Int = {
    getModerators.length
  }

  def findAModerator(): Option[UserVO] = {
    users.values find (u => u.roles.contains(Role.MODERATOR))
  }

  def noPresenter(): Boolean = {
    !getCurrentPresenter().isDefined
  }

  def getCurrentPresenter(): Option[UserVO] = {
    users.values find (u => u.presenter == true)
  }

  def unbecomePresenter(userID: String) = {
    users.get(userID) match {
      case Some(u) => {
        val nu = u.copy(presenter = IsPresenter(false))
        users += nu.id.value -> nu
      }
      case None => // do nothing  
    }
  }

  def becomePresenter(userId: IntUserId) = {
    users.get(userId.value) match {
      case Some(u) => {
        val nu = u.copy(presenter = IsPresenter(true))
        users += nu.id.value -> nu
      }
      case None => // do nothing  
    }
  }

  def getModerators(): Array[UserVO] = {
    users.values filter (u => u.roles.contains(Role.MODERATOR)) toArray
  }

  def getViewers(): Array[UserVO] = {
    users.values filter (u => u.roles.contains(Role.VIEWER)) toArray
  }

  def addGlobalAudioConnection(userId: IntUserId): Boolean = {
    globalAudioConnectionCounter.get(userId.value) match {
      case Some(vc) => {
        globalAudioConnectionCounter += userId.value -> (vc + 1)
        false
      }
      case None => {
        globalAudioConnectionCounter += userId.value -> 1
        true
      }
    }
  }

  def removeGlobalAudioConnection(userId: IntUserId): Boolean = {
    globalAudioConnectionCounter.get(userId.value) match {
      case Some(vc) => {
        if (vc == 1) {
          globalAudioConnectionCounter -= userId.value
          true
        } else {
          globalAudioConnectionCounter += userId.value -> (vc - 1)
          false
        }
      }
      case None => {
        false
      }
    }
  }

  def startRecordingVoice() {
    recordingVoice = true
  }

  def stopRecordingVoice() {
    recordingVoice = false
  }

  def isVoiceRecording: Boolean = {
    recordingVoice
  }
}