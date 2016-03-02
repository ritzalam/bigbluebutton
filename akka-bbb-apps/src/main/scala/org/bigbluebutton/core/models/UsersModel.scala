package org.bigbluebutton.core.models

import scala.collection.mutable.HashMap
import org.bigbluebutton.core.models.Role._
import scala.collection.mutable.ArrayBuffer
import org.bigbluebutton.core.util.RandomStringGenerator

class UsersModel {
  private var uservos = new collection.immutable.HashMap[String, UserVO]

  private var regUsers = new collection.immutable.HashMap[String, RegisteredUser]

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

  def addRegisteredUser(token: String, regUser: RegisteredUser) {
    regUsers += token -> regUser
  }

  def getRegisteredUserWithToken(token: AuthToken): Option[RegisteredUser] = {
    regUsers.get(token.value)
  }

  def generateWebUserId: IntUserId = {
    val webUserId = IntUserId(RandomStringGenerator.randomAlphanumericString(6))
    if (!hasUser(webUserId)) webUserId else generateWebUserId
  }

  def addUser(uvo: UserVO) {
    uservos += uvo.id.value -> uvo
  }

  def removeUser(userId: IntUserId): Option[UserVO] = {
    val user = uservos get (userId.value)
    user foreach (u => uservos -= userId.value)

    user
  }

  def hasSessionId(sessionId: String): Boolean = {
    uservos.contains(sessionId)
  }

  def hasUser(userId: IntUserId): Boolean = {
    uservos.contains(userId.value)
  }

  def numUsers(): Int = {
    uservos.size
  }

  def numWebUsers(): Int = {
    uservos.values filter (u => u.phoneUser == false) size
  }

  def numUsersInVoiceConference: Int = {
    val joinedUsers = uservos.values filter (u => u.voiceUser.joinedVoice.value)
    joinedUsers.size
  }

  def getUserWithExternalId(userID: String): Option[UserVO] = {
    uservos.values find (u => u.extId.value == userID)
  }

  def getUserWithVoiceUserId(voiceUserId: VoiceUserId): Option[UserVO] = {
    uservos.values find (u => u.voiceUser.id.value == voiceUserId.value)
  }

  def getUser(userId: IntUserId): Option[UserVO] = {
    uservos.values find (u => u.id.value == userId.value)
  }

  def getUsers(): Array[UserVO] = {
    uservos.values toArray
  }

  def numModerators(): Int = {
    getModerators.length
  }

  def findAModerator(): Option[UserVO] = {
    uservos.values find (u => u.role == MODERATOR)
  }

  def noPresenter(): Boolean = {
    !getCurrentPresenter().isDefined
  }

  def getCurrentPresenter(): Option[UserVO] = {
    uservos.values find (u => u.presenter == true)
  }

  def unbecomePresenter(userID: String) = {
    uservos.get(userID) match {
      case Some(u) => {
        val nu = u.copy(presenter = IsPresenter(false))
        uservos += nu.id.value -> nu
      }
      case None => // do nothing	
    }
  }

  def becomePresenter(userId: IntUserId) = {
    uservos.get(userId.value) match {
      case Some(u) => {
        val nu = u.copy(presenter = IsPresenter(true))
        uservos += nu.id.value -> nu
      }
      case None => // do nothing	
    }
  }

  def getModerators(): Array[UserVO] = {
    uservos.values filter (u => u.role == MODERATOR) toArray
  }

  def getViewers(): Array[UserVO] = {
    uservos.values filter (u => u.role == VIEWER) toArray
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