package org.bigbluebutton.core.handlers

import scala.collection.immutable.ListSet
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.api.RegisteredUser
import org.bigbluebutton.core.api.UserVO
import org.bigbluebutton.core.api.VoiceUser
import org.bigbluebutton.core.api.Role

trait UsersApp {
  this: LiveMeeting =>

  def findRegisteredUserWithToken(authToken: String): Option[RegisteredUser] = {
    usersModel.getRegisteredUserWithToken(authToken)
  }

  def findUser(userId: String): Option[UserVO] = {
    usersModel.getUser(userId)
  }

  def becomePresenterIfOnlyModerator(userId: String, name: String, role: Role.Role) {
    if ((usersModel.numModerators == 1) || (usersModel.noPresenter())) {
      if (role == Role.MODERATOR) {
        assignNewPresenter(userId, name, userId)
      }
    }
  }

  def changeUserEmojiStatus(userId: String, emojiStatus: String): Option[UserVO] = {
    val vu = for {
      user <- usersModel.getUser(userId)
       uvo = user.copy(emojiStatus = emojiStatus)
    } yield uvo

    vu foreach { u =>
      usersModel.addUser(u)
    }

    vu
  }

  def createNewUser(userId: String, externId: String,
    name: String, role: Role.Role,
    voiceUser: VoiceUser, locked: Boolean): UserVO = {
    // Initialize the newly joined user copying voice status in case this
    // join is due to a reconnect.
    val uvo = new UserVO(userId, externId, name,
      role, emojiStatus = "none", presenter = false,
      hasStream = false, locked = locked,
      webcamStreams = new ListSet[String](), phoneUser = false, voiceUser,
      listenOnly = voiceUser.listenOnly, joinedWeb = true)
    usersModel.addUser(uvo)
    uvo
  }

  def initializeVoice(userId: String, username: String): VoiceUser = {
    val wUser = usersModel.getUser(userId)

    val vu = wUser match {
      case Some(u) => {
        if (u.voiceUser.joined) {
          // User is in voice conference. Must mean that the user reconnected with audio
          // still in the voice conference.
          u.voiceUser.copy()
        } else {
          // User is not joined in voice conference. Initialize user and copy status
          // as user maybe joined listenOnly.
          new VoiceUser(u.voiceUser.userId, userId, username, username,
            joined = false, locked = false, muted = false,
            talking = false, listenOnly = u.listenOnly)
        }
      }
      case None => {
        // New user. Initialize voice status.
        new VoiceUser(userId, userId, username, username,
          joined = false, locked = false,
          muted = false, talking = false, listenOnly = false)
      }
    }

    vu
  }
}