package org.bigbluebutton.core.handlers

import scala.collection.immutable.ListSet
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.models._

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
      uvo = user.copy(emojiStatus = EmojiStatus(emojiStatus))
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
    val uvo = new UserVO(IntUserId(userId), ExtUserId(externId), Name(name),
      role, emojiStatus = EmojiStatus("none"), presenter = IsPresenter(false),
      hasStream = HasStream(false), locked = Locked(locked),
      webcamStreams = new ListSet[String](), phoneUser = PhoneUser(false), voiceUser,
      listenOnly = voiceUser.listenOnly, joinedWeb = JoinedWeb(true))
    usersModel.addUser(uvo)
    uvo
  }

  def initializeVoice(userId: String, username: String): VoiceUser = {
    val wUser = usersModel.getUser(userId)

    val vu = wUser match {
      case Some(u) => {
        if (u.voiceUser.joinedVoice.value) {
          // User is in voice conference. Must mean that the user reconnected with audio
          // still in the voice conference.
          u.voiceUser.copy()
        } else {
          // User is not joined in voice conference. Initialize user and copy status
          // as user maybe joined listenOnly.
          val callerId = CallerId(CallerIdName(username), CallerIdNum(username))
          new VoiceUser(u.voiceUser.id, IntUserId(userId), callerId,
            joinedVoice = JoinedVoice(false), locked = Locked(false), muted = Muted(false),
            talking = Talking(false), listenOnly = u.listenOnly)
        }
      }
      case None => {
        // New user. Initialize voice status.
        val callerId = CallerId(CallerIdName(username), CallerIdNum(username))
        new VoiceUser(VoiceUserId(userId), IntUserId(userId), callerId,
          joinedVoice = JoinedVoice(false), locked = Locked(false),
          muted = Muted(false), talking = Talking(false), listenOnly = ListenOnly(false))
      }
    }

    vu
  }
}