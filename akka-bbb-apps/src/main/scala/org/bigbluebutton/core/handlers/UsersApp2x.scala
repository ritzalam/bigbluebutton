package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api.RecordingStatusChanged
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.models.{ Meeting2x }

import scala.collection.immutable.ListSet

trait UsersApp2x {
  val meeting: Meeting2x
  def assignNewPresenter(newPresenterId: IntUserId, newPresenterName: Name, assignedBy: IntUserId) {
    // Stop poll if one is running as presenter left.
    //    handleStopPollRequest(StopPollRequest(props.id, assignedBy))

    //    if (meeting.hasUser(newPresenterId)) {

    //      meeting.getCurrentPresenter match {
    //        case Some(curPres) =>
    //          meeting.unbecomePresenter(curPres.id.value)
    //          sender.sendUserStatusChangeMessage(props.id, props.recorded, curPres.id, false)

    //        case None => // do nothing
    //      }

    //      meeting.getUser(newPresenterId) match {
    //        case Some(newPres) =>
    //          meeting.becomePresenter(newPres.id)
    //          meeting.setCurrentPresenterInfo(new Presenter(newPresenterId, newPresenterName, assignedBy))
    //          sender.sendPresenterAssignedMessage(props.id, props.recorded, new Presenter(newPresenterId, newPresenterName, assignedBy))
    //          sender.sendUserStatusChangeMessage(props.id, props.recorded, newPresenterId, true)

    //        case None => // do nothing
    //      }

    //    }
  }

  def webUserJoined() {
    //    if (meeting.numWebUsers > 0) {
    //      meeting.resetLastWebUserLeftOn()
    //    }
  }

  def needToStartRecording(meeting: Meeting2x): Boolean = {
    meeting.props.recorded.value &&
      !meeting.isRecording &&
      meeting.props.autoStartRecording //&&
    //Users2x.numberOfWebUsers(meeting.state.users.toVector) == 1
  }

  def becomePresenterIfOnlyModerator(userId: IntUserId, name: Name, roles: Set[Role2x]) {
    //    if ((meeting.numModerators == 1) || (meeting.noPresenter())) {
    //      if (roles.contains(Role.MODERATOR)) {
    //        assignNewPresenter(userId, name, userId)
    //      }
    //    }
  }

  //  def changeUserEmojiStatus(userId: IntUserId, emojiStatus: EmojiStatus): Option[UserVO] = {
  //    val vu = for {
  //      user <- meeting.getUser(userId)
  //      uvo = user.copy(emojiStatus = emojiStatus)
  //    } yield uvo

  //    vu foreach { u =>
  //      meeting.saveUser(u)
  //    }

  //    vu
  //  }
  //
  //  def createNewUser(userId: IntUserId, externId: ExtUserId,
  //    name: Name, roles: Set[String],
  //    voiceUser: VoiceUser, locked: Boolean): UserVO = {
  // Initialize the newly joined user copying voice status in case this
  // join is due to a reconnect.
  //    val uvo = new UserVO(userId, externId, name,
  //      roles, emojiStatus = EmojiStatus("none"), presenter = IsPresenter(false),
  //      hasStream = HasStream(false), locked = Locked(locked),
  //      webcamStreams = new ListSet[String](), phoneUser = PhoneUser(false), voiceUser,
  //      listenOnly = voiceUser.listenOnly, joinedWeb = JoinedWeb(true))
  //    meeting.saveUser(uvo)
  //    uvo
  //  }

  //  def initializeVoice(userId: IntUserId, username: Name): VoiceUser = {
  //    val wUser = meeting.getUser(userId)

  //    val vu = wUser match {
  //     case Some(u) => {
  //        if (u.voiceUser.joinedVoice.value) {
  //          // User is in voice conference. Must mean that the user reconnected with audio
  //          // still in the voice conference.
  //          u.voiceUser.copy()
  //        } else {
  //          // User is not joined in voice conference. Initialize user and copy status
  //          // as user maybe joined listenOnly.
  //          val callerId = CallerId(CallerIdName(username.value), CallerIdNum(username.value))
  //          new VoiceUser(u.voiceUser.id, userId, callerId,
  //            joinedVoice = JoinedVoice(false), locked = Locked(false), muted = Muted(false),
  //            talking = Talking(false), listenOnly = u.listenOnly)
  //        }
  //      }
  //      case None => {
  //        // New user. Initialize voice status.
  //        val callerId = CallerId(CallerIdName(username.value), CallerIdNum(username.value))
  //        new VoiceUser(VoiceUserId(userId.value), userId, callerId,
  //          joinedVoice = JoinedVoice(false), locked = Locked(false),
  //          muted = Muted(false), talking = Talking(false), listenOnly = ListenOnly(false))
  //      }
  //    }

  //    vu
  //  }
}
