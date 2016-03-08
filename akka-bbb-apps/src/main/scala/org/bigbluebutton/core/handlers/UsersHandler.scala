package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api._
import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.ListSet
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.models._
import org.bigbluebutton.core.models.VoiceUser
import org.bigbluebutton.core.models.UserVO
import org.bigbluebutton.core.models.RegisteredUser

trait UsersHandler extends UsersApp with UsersMessageSender {
  this: LiveMeeting =>

  val outGW: OutMessageGateway

  def handleUserConnectedToGlobalAudio(msg: UserConnectedToGlobalAudio) {
    log.info("Handling UserConnectedToGlobalAudio: meetingId=" + mProps.id + " userId=" + msg.userId)

    val user = meeting.getUser(msg.userId)
    user foreach { u =>
      if (meeting.addGlobalAudioConnection(msg.userId)) {
        val vu = u.voiceUser.copy(joinedVoice = JoinedVoice(false), talking = Talking(false))
        val uvo = u.copy(listenOnly = ListenOnly(true), voiceUser = vu)
        meeting.addUser(uvo)
        log.info("UserConnectedToGlobalAudio: meetingId=" + mProps.id + " userId=" + uvo.id + " user=" + uvo)
        sendUserListeningOnlyMessage(mProps.id, mProps.recorded, uvo.id, uvo.listenOnly)
      }
    }
  }

  def handleUserDisconnectedFromGlobalAudio(msg: UserDisconnectedFromGlobalAudio) {
    log.info("Handling UserDisconnectedToGlobalAudio: meetingId=" + mProps.id + " userId=" + msg.userId)

    val user = meeting.getUser(msg.userId)
    user foreach { u =>
      if (meeting.removeGlobalAudioConnection(msg.userId)) {
        if (!u.joinedWeb.value) {
          val userLeaving = meeting.removeUser(u.id)
          log.info("Not web user. Send user left message. meetingId=" + mProps.id + " userId=" + u.id + " user=" + u)
          userLeaving foreach (u => sendUerLeftMessage(mProps.id, mProps.recorded, u))
        } else {
          val vu = u.voiceUser.copy(joinedVoice = JoinedVoice(false))
          val uvo = u.copy(listenOnly = ListenOnly(false), voiceUser = vu)
          meeting.addUser(uvo)
          log.info("UserDisconnectedToGlobalAudio: meetingId=" + mProps.id + " userId=" + uvo.id + " user=" + uvo)
          sendUserListeningOnlyMessage(mProps.id, mProps.recorded, uvo.id, uvo.listenOnly)
        }
      }
    }
  }

  def handleMuteAllExceptPresenterRequest(msg: MuteAllExceptPresenterRequest) {
    if (msg.mute) {
      meeting.muteMeeting()
    } else {
      meeting.unmuteMeeting()
    }

    sendMeetingMutedMessage(mProps.id, mProps.recorded, meeting.isMeetingMuted())
    usersWhoAreNotPresenter foreach { u =>
      sendMuteVoiceUserMessage(mProps.id, mProps.recorded, u.id, msg.requesterId,
        u.voiceUser.id, mProps.voiceBridge, msg.mute)
    }
  }

  def handleMuteMeetingRequest(msg: MuteMeetingRequest) {
    if (msg.mute) {
      meeting.muteMeeting()
    } else {
      meeting.unmuteMeeting()
    }
    sendMeetingMutedMessage(mProps.id, mProps.recorded, meeting.isMeetingMuted())
    meeting.getUsers foreach { u =>
      sendMuteVoiceUserMessage(mProps.id, mProps.recorded, u.id, msg.requesterId,
        u.voiceUser.id, mProps.voiceBridge, msg.mute)
    }
  }

  def handleValidateAuthToken(msg: ValidateAuthToken) {
    log.info("Got ValidateAuthToken message. meetingId=" + msg.meetingId + " userId=" + msg.userId)
    meeting.findWithToken(msg.token) match {
      case Some(u) =>
        {
          val replyTo = mProps.id.value + '/' + msg.userId

          //send the reply
          sendValidateAuthTokenReplyMessage(mProps.id, msg.userId, msg.token, true, msg.correlationId)
          log.info("ValidateToken success. meetingId=" + mProps.id + " userId=" + msg.userId)

          //join the user
          handleUserJoin(new UserJoining(mProps.id, msg.userId, msg.token))

        }
      case None => {
        log.info("ValidateToken failed. meetingId=" + mProps.id + " userId=" + msg.userId)
        sendValidateAuthTokenReplyMessage(mProps.id, msg.userId, msg.token, false, msg.correlationId)
      }
    }

    /**
     * Send a reply to BigBlueButtonActor to let it know this MeetingActor hasn't hung!
     * Sometimes, the actor seems to hang and doesn't anymore accept messages. This is a simple
     * audit to check whether the actor is still alive. (ralam feb 25, 2015)
     */
    //sender ! new ValidateAuthTokenReply(mProps.meetingID, msg.userId, msg.token, false, msg.correlationId)
  }

  def handleRegisterUser(msg: RegisterUser) {
    if (meeting.hasMeetingEnded()) {
      // Check first if the meeting has ended and the user refreshed the client to re-connect.
      log.info("Register user failed. Mmeeting has ended. meetingId=" + mProps.id + " userId=" + msg.userId)
      sendMeetingHasEnded(mProps.id, msg.userId)
    } else {
      for {
        regUser <- meeting.createRegisteredUser(msg.userId, msg.extUserId, msg.name, msg.role, msg.authToken)
        rusers = meeting.addRegisteredUser(msg.authToken, regUser)
      } yield sendUserRegisteredMessage(mProps.id, mProps.recorded, regUser)
    }
  }

  def handleIsMeetingMutedRequest(msg: IsMeetingMutedRequest) {
    sendIsMeetingMutedReplyMessage(mProps.id, mProps.recorded, msg.requesterId, meeting.isMeetingMuted())
  }

  def handleMuteUserRequest(msg: MuteUserRequest) {
    log.info("Received mute user request. meetingId=" + mProps.id + " userId=" + msg.userId + " mute=" + msg.mute)
    meeting.getUser(msg.userId) match {
      case Some(u) => {
        log.info("Send mute user request. meetingId=" + mProps.id + " userId=" + u.id + " user=" + u)
        sendMuteVoiceUserMessage(mProps.id, mProps.recorded, u.id, msg.requesterId,
          u.voiceUser.id, mProps.voiceBridge, msg.mute)
      }
      case None => {
        log.info("Could not find user to mute.  meetingId=" + mProps.id + " userId=" + msg.userId)
      }
    }
  }

  def handleEjectUserRequest(msg: EjectUserFromVoiceRequest) {
    log.info("Received eject user request. meetingId=" + msg.meetingId + " userId=" + msg.userId)
    meeting.getUser(msg.userId) match {
      case Some(u) => {
        if (u.voiceUser.joinedVoice.value) {
          log.info("Ejecting user from voice.  meetingId=" + mProps.id + " userId=" + u.id)
          sendEjectVoiceUserMessage(mProps.id, mProps.recorded, msg.ejectedBy, u.id,
            u.voiceUser.id, mProps.voiceBridge)
        }
      }
      case None => // do nothing
    }
  }

  def handleGetLockSettings(msg: GetLockSettings) {
    //println("*************** Reply with current lock settings ********************")
    //reusing the existing handle for NewPermissionsSettings to reply to the GetLockSettings request
    sendNewPermissionsSettingMessage(mProps.id, msg.userId, meeting.getPermissions(), meeting.getUsers)
  }

  def handleSetLockSettings(msg: SetLockSettings) {
    if (!permissionsEqual(msg.settings)) {
      newPermissions(msg.settings)
      sendNewPermissionsSettingMessage(mProps.id, msg.setByUser, meeting.getPermissions(), meeting.getUsers)
      handleLockLayout(msg.settings.lockedLayout, msg.setByUser)
    }
  }

  def handleLockUserRequest(msg: LockUserRequest) {
    meeting.getUser(msg.userId) match {
      case Some(u) => {
        val uvo = u.copy(locked = Locked(msg.lock))
        meeting.addUser(uvo)

        log.info("Lock user.  meetingId=" + mProps.id + " userId=" + u.id + " lock=" + msg.lock)
        sendUserLockedMessage(mProps.id, u.id, uvo.locked)
      }
      case None => {
        log.info("Could not find user to lock.  meetingId=" + mProps.id + " userId=" + msg.userId + " lock=" + msg.lock)
      }
    }
  }

  def handleInitLockSettings(msg: InitLockSettings) {
    if (!meeting.permisionsInitialized()) {
      meeting.initializePermissions()
      newPermissions(msg.settings)
      sendPermissionsSettingInitializedMessage(msg.meetingId, msg.settings, meeting.getUsers)
    }
  }

  def handleInitAudioSettings(msg: InitAudioSettings) {
    if (!meeting.audioSettingsInitialized()) {
      meeting.initializeAudioSettings()

      if (meeting.isMeetingMuted() != msg.muted) {
        handleMuteAllExceptPresenterRequest(new MuteAllExceptPresenterRequest(mProps.id, msg.requesterId, msg.muted));
      }
    }
  }

  def usersWhoAreNotPresenter(): Array[UserVO] = {
    val au = ArrayBuffer[UserVO]()

    meeting.getUsers foreach { u =>
      if (!u.presenter.value) {
        au += u
      }
    }
    au.toArray
  }

  def handleUserEmojiStatus(msg: UserEmojiStatus) {
    val userVO = changeUserEmojiStatus(msg.userId, msg.emojiStatus)

    userVO foreach { uvo =>
      sendUserChangedEmojiStatusMessage(mProps.id, mProps.recorded, uvo.emojiStatus, uvo.id)
    }
  }

  def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    meeting.getUser(msg.userId) foreach { user =>
      if (user.voiceUser.joinedVoice.value) {
        sendEjectVoiceUserMessage(mProps.id, mProps.recorded,
          msg.ejectedBy, msg.userId, user.voiceUser.id, mProps.voiceBridge)
      }

      meeting.removeUser(msg.userId)
      meeting.removeRegisteredUser(msg.userId)

      log.info("Ejecting user from meeting.  meetingId=" + mProps.id + " userId=" + msg.userId)
      sendUserEjectedFromMeetingMessage(mProps.id, mProps.recorded, msg.userId, msg.ejectedBy)
      sendDisconnectUserMessage(mProps.id, msg.userId)
      sendUserLeftMessage(msg.meetingId, mProps.recorded, user)
    }
  }

  def handleUserShareWebcam(msg: UserShareWebcam) {
    meeting.getUser(msg.userId) foreach { user =>
      val streams = user.webcamStreams + msg.stream
      val uvo = user.copy(hasStream = HasStream(true), webcamStreams = streams)
      meeting.addUser(uvo)
      log.info("User shared webcam.  meetingId=" + mProps.id + " userId=" + uvo.id
        + " stream=" + msg.stream + " streams=" + streams)
      sendUserSharedWebcamMessage(mProps.id, mProps.recorded, uvo.id, msg.stream)
    }
  }

  def handleUserunshareWebcam(msg: UserUnshareWebcam) {
    meeting.getUser(msg.userId) foreach { user =>
      val streamName = user.webcamStreams find (w => w == msg.stream) foreach { streamName =>
        val streams = user.webcamStreams - streamName
        val uvo = user.copy(hasStream = HasStream((!streams.isEmpty)), webcamStreams = streams)
        meeting.addUser(uvo)
        log.info("User unshared webcam.  meetingId=" + mProps.id + " userId=" + uvo.id
          + " stream=" + msg.stream + " streams=" + streams)
        sendUserUnsharedWebcamMessage(mProps.id, mProps.recorded, uvo.id, msg.stream)
      }
    }
  }

  def handleChangeUserStatus(msg: ChangeUserStatus): Unit = {
    if (meeting.hasUser(msg.userId)) {
      sendUserStatusChangeMessage(mProps.id, mProps.recorded, msg.userId, msg.status, msg.value)
    }
  }

  def handleGetUsers(msg: GetUsers): Unit = {
    sendGetUsersReplyMessage(msg.meetingId, msg.requesterId, meeting.getUsers)
  }

  def sendUserLeftEvent(user: UserVO) {
    val u = meeting.removeUser(user.id)
    sendUserLeftMessage(mProps.id, mProps.recorded, user)
  }

  def handleUserJoin(msg: UserJoining): Unit = {
    log.debug("Received user joined meeting. metingId=" + mProps.id + " userId=" + msg.userId)

    val regUser = findRegisteredUserWithToken(msg.token)
    val webUser = findUser(msg.userId)
    webUser foreach { wu =>
      if (!wu.joinedWeb.value) {
        /**
         * If user is not joined through the web (perhaps reconnecting).
         * Send a user left event to clear up user list of all clients.
         */
        sendUserLeftEvent(wu)
      }
    }

    regUser foreach { ru =>
      val voiceUser = initializeVoice(msg.userId, ru.name)
      val locked = getInitialLockStatus(ru.role)
      val uvo = createNewUser(msg.userId, ru.extId, ru.name, ru.role, voiceUser, getInitialLockStatus(ru.role))

      log.info("User joined meeting. metingId=" + mProps.id + " userId=" + uvo.id + " user=" + uvo)

      sendUserJoinedMessage(mProps.id, mProps.recorded, uvo)
      sendMeetingStateMessage(mProps.id, mProps.recorded, uvo.id, meeting.getPermissions(),
        Muted(meeting.isMeetingMuted()))

      becomePresenterIfOnlyModerator(msg.userId, ru.name, ru.role)
    }

    webUserJoined
    startRecordingIfAutoStart()
  }

  def handleUserJoin2(msg: UserJoining): Unit = {
    log.debug("Received user joined meeting. metingId=" + mProps.id + " userId=" + msg.userId)

    val regUser = meeting.findWithToken(msg.token)
    regUser foreach { ru =>
      log.debug("Found registered user. metingId=" + mProps.id + " userId=" + msg.userId + " ru=" + ru)

      val wUser = meeting.getUser(msg.userId)

      val vu = wUser match {
        case Some(u) => {
          log.debug("Found  user. metingId=" + mProps.id + " userId=" + msg.userId + " user=" + u)
          if (u.voiceUser.joinedVoice.value) {
            /*
             * User is in voice conference. Must mean that the user reconnected with audio
             * still in the voice conference.
             */
            u.voiceUser.copy()
          } else {
            /**
             * User is not joined in voice conference. Initialize user and copy status
             * as user maybe joined listenOnly.
             */
            val callerId = CallerId(CallerIdName(ru.name.value), CallerIdNum(ru.name.value))
            new VoiceUser(u.voiceUser.id, msg.userId, callerId,
              joinedVoice = JoinedVoice(false), locked = Locked(false), muted = Muted(false),
              talking = Talking(false), listenOnly = u.listenOnly)
          }
        }
        case None => {
          log.debug("User not found. metingId=" + mProps.id + " userId=" + msg.userId)
          /**
           * New user. Initialize voice status.
           */
          val callerId = CallerId(CallerIdName(ru.name.value), CallerIdNum(ru.name.value))
          new VoiceUser(VoiceUserId(msg.userId.value), msg.userId, callerId,
            joinedVoice = JoinedVoice(false), locked = Locked(false),
            muted = Muted(false), talking = Talking(false), listenOnly = ListenOnly(false))
        }
      }

      wUser.foreach { w =>
        if (!w.joinedWeb.value) {
          log.debug("User is in voice only. Mark as user left. metingId=" + mProps.id + " userId=" + msg.userId)
          /**
           * If user is not joined through the web (perhaps reconnecting).
           * Send a user left event to clear up user list of all clients.
           */
          val user = meeting.removeUser(w.id)
          sendUserLeftMessage(msg.meetingId, mProps.recorded, w)
        }
      }

      /**
       * Initialize the newly joined user copying voice status in case this
       * join is due to a reconnect.
       */
      val uvo = new UserVO(msg.userId, ru.extId, ru.name,
        ru.role, emojiStatus = EmojiStatus("none"), presenter = IsPresenter(false),
        hasStream = HasStream(false), locked = Locked(getInitialLockStatus(ru.role)),
        webcamStreams = new ListSet[String](), phoneUser = PhoneUser(false), vu,
        listenOnly = vu.listenOnly, joinedWeb = JoinedWeb(true))

      meeting.addUser(uvo)

      log.info("User joined meeting. metingId=" + mProps.id + " userId=" + uvo.id + " user=" + uvo)

      sendUserJoinedMessage(mProps.id, mProps.recorded, uvo)
      sendMeetingStateMessage(mProps.id, mProps.recorded, uvo.id,
        meeting.getPermissions(), Muted(meeting.isMeetingMuted()))

      // Become presenter if the only moderator		
      if ((meeting.numModerators == 1) || (meeting.noPresenter())) {
        if (ru.role == Role.MODERATOR) {
          assignNewPresenter(msg.userId, ru.name, msg.userId)
        }
      }
      webUserJoined
      startRecordingIfAutoStart()
    }
  }

  def handleUserLeft(msg: UserLeaving): Unit = {
    if (meeting.hasUser(msg.userId)) {
      val user = meeting.removeUser(msg.userId)
      user foreach { u =>
        log.info("User left meeting. meetingId=" + mProps.id + " userId=" + u.id + " user=" + u)
        sendUserLeftMessage(msg.meetingId, mProps.recorded, u)

        if (u.presenter.value) {

          /* The current presenter has left the meeting. Find a moderator and make
	       * him presenter. This way, if there is a moderator in the meeting, there
	       * will always be a presenter.
	       */
          val moderator = meeting.findAModerator()
          moderator.foreach { mod =>
            log.info("Presenter left meeting.  meetingId=" + mProps.id + " userId=" + u.id
              + ". Making user=[" + mod.id + "] presenter.")
            assignNewPresenter(mod.id, mod.name, mod.id)
          }
        }

        val vu = u.voiceUser
        if (vu.joinedVoice.value || u.listenOnly.value) {
          /**
           * The user that left is still in the voice conference. Maybe this user just got disconnected
           * and is reconnecting. Make the user as joined only in the voice conference. If we get a
           * user left voice conference message, then we will remove the user from the users list.
           */
          switchUserToPhoneUser((new UserJoinedVoiceConfMessage(mProps.voiceBridge,
            vu.id, u.id, u.extId, vu.callerId,
            vu.muted, vu.talking, u.listenOnly)));
        }

        checkCaptionOwnerLogOut(u.id.value)
      }

      startCheckingIfWeNeedToEndVoiceConf()
      stopAutoStartedRecording()
    }
  }

  def getInitialLockStatus(role: Role.Role): Boolean = {
    meeting.getPermissions().lockOnJoin && !role.equals(Role.MODERATOR)
  }

  def handleUserJoinedVoiceFromPhone(msg: UserJoinedVoiceConfMessage) = {
    log.info("User joining from phone.  meetingId=" + mProps.id + " userId=" + msg.userId + " extUserId=" + msg.externUserId)

    val user = meeting.getUserWithVoiceUserId(msg.voiceUserId) match {
      case Some(user) => {
        log.info("Voice user=" + msg.voiceUserId + " is already in conf="
          + mProps.voiceBridge + ". Must be duplicate message. meetigId=" + mProps.id)
      }
      case None => {
        val webUserId = if (msg.userId.value != msg.callerId.name.value) {
          msg.userId
        } else {
          // No current web user. This means that the user called in through
          // the phone. We need to generate a new user as we are not able
          // to match with a web user.
          meeting.generateWebUserId(meeting.getUsers)
        }

        /**
         * If user is not joined listenOnly then user is joined calling through phone or webrtc.
         */
        val vu = new VoiceUser(msg.voiceUserId, webUserId, msg.callerId,
          joinedVoice = JoinedVoice(!msg.listenOnly.value), locked = Locked(false),
          muted = msg.muted, talking = msg.talking, listenOnly = msg.listenOnly)

        /**
         * If user is not joined listenOnly then user is joined calling through phone or webrtc.
         * So we call him "phoneUser".
         */
        val uvo = new UserVO(webUserId, msg.externUserId, Name(msg.callerId.name.value),
          Role.VIEWER, emojiStatus = EmojiStatus("none"), presenter = IsPresenter(false),
          hasStream = HasStream(false), locked = Locked(getInitialLockStatus(Role.VIEWER)),
          webcamStreams = new ListSet[String](),
          phoneUser = PhoneUser(!(msg.listenOnly.value)), vu, listenOnly = msg.listenOnly,
          joinedWeb = JoinedWeb(false))

        meeting.addUser(uvo)

        log.info("User joined from phone.  meetingId=" + mProps.id + " userId=" + uvo.id + " user=" + uvo)
        sendUserJoinedMessage(mProps.id, mProps.recorded, uvo)
        sendUserJoinedVoiceMessage(mProps.id, mProps.recorded, mProps.voiceBridge, uvo)

        if (meeting.isMeetingMuted()) {
          sendMuteVoiceUserMessage(mProps.id, mProps.recorded, uvo.id, uvo.id,
            vu.id, mProps.voiceBridge, meeting.isMeetingMuted())
        }
      }
    }
  }

  def startRecordingVoiceConference() {
    if (meeting.numUsersInVoiceConference == 1 && mProps.recorded.value && !meeting.isVoiceRecording) {
      meeting.startRecordingVoice()
      log.info("Send START RECORDING voice conf. meetingId=" + mProps.id + " voice conf=" + mProps.voiceBridge)
      sendStartRecordingVoiceConf(mProps.id, mProps.recorded, mProps.voiceBridge)
    }
  }

  def switchUserToPhoneUser(msg: UserJoinedVoiceConfMessage) = {
    log.info("User has been disconnected but still in voice conf. Switching to phone user. meetingId="
      + mProps.id + " callername=" + msg.callerId.name
      + " userId=" + msg.userId + " extUserId=" + msg.externUserId)

    meeting.getUser(msg.userId) match {
      case Some(user) => {
        val vu = new VoiceUser(msg.voiceUserId, msg.userId, msg.callerId,
          joinedVoice = JoinedVoice(true), locked = Locked(false),
          muted = msg.muted, talking = msg.talking, listenOnly = msg.listenOnly)
        val nu = user.copy(voiceUser = vu, listenOnly = msg.listenOnly)
        meeting.addUser(nu)

        log.info("User joined voice. meetingId=" + mProps.id + " userId=" + user.id + " user=" + nu)
        sendUserJoinedVoiceMessage(mProps.id, mProps.recorded, mProps.voiceBridge, nu)

        if (meeting.isMeetingMuted()) {
          sendMuteVoiceUserMessage(mProps.id, mProps.recorded, nu.id, nu.id,
            nu.voiceUser.id, mProps.voiceBridge, meeting.isMeetingMuted())
        }
      }
      case None => {
        handleUserJoinedVoiceFromPhone(msg)
      }
    }
  }

  def handleUserJoinedVoiceConfMessage(msg: UserJoinedVoiceConfMessage) = {
    log.info("Received user joined voice. meetingId=" + mProps.id + " callername=" + msg.callerId.name
      + " userId=" + msg.userId + " extUserId=" + msg.externUserId)

    meeting.getUser(msg.userId) match {
      case Some(user) => {
        // this is used to restore the mute state on reconnect
        val previouslyMuted = user.voiceUser.muted

        val vu = new VoiceUser(msg.voiceUserId, msg.userId, msg.callerId,
          joinedVoice = JoinedVoice(true), locked = Locked(false),
          muted = msg.muted, talking = msg.talking, listenOnly = msg.listenOnly)
        val nu = user.copy(voiceUser = vu, listenOnly = msg.listenOnly)
        meeting.addUser(nu)

        log.info("User joined voice. meetingId=" + mProps.id + " userId=" + user.id + " user=" + nu)

        sendUserJoinedVoiceMessage(mProps.id, mProps.recorded, mProps.voiceBridge, nu)

        if (meeting.isMeetingMuted() || previouslyMuted.value) {
          sendMuteVoiceUserMessage(mProps.id, mProps.recorded, nu.id, nu.id,
            nu.voiceUser.id, mProps.voiceBridge, true)
        }

        startRecordingVoiceConference()
      }
      case None => {
        handleUserJoinedVoiceFromPhone(msg)
        startRecordingVoiceConference()
      }
    }
  }

  def stopRecordingVoiceConference() {
    if (meeting.numUsersInVoiceConference == 0 && mProps.recorded.value && meeting.isVoiceRecording) {
      meeting.stopRecordingVoice()
      log.info("Send STOP RECORDING voice conf. meetingId=" + mProps.id + " voice conf=" + mProps.voiceBridge)
      sendStopRecordingVoiceConf(mProps.id, mProps.recorded,
        mProps.voiceBridge, meeting.getVoiceRecordingFilename())
    }
  }

  def handleUserLeftVoiceConfMessage(msg: UserLeftVoiceConfMessage) {
    log.info("Received user left voice conf. meetingId=" + mProps.id + " voice conf=" + msg.voiceConfId
      + " userId=" + msg.voiceUserId)

    meeting.getUserWithVoiceUserId(msg.voiceUserId) foreach { user =>
      /**
       * Reset user's voice status.
       */
      val callerId = CallerId(CallerIdName(user.name.value), CallerIdNum(user.name.value))
      val vu = new VoiceUser(VoiceUserId(user.id.value), user.id, callerId,
        joinedVoice = JoinedVoice(false), locked = Locked(false),
        muted = Muted(false), talking = Talking(false), listenOnly = ListenOnly(false))
      val nu = user.copy(voiceUser = vu, phoneUser = PhoneUser(false), listenOnly = ListenOnly(false))
      meeting.addUser(nu)

      log.info("User left voice conf. meetingId=" + mProps.id + " userId=" + nu.id + " user=" + nu)

      sendUserLeftVoiceMessage(mProps.id, mProps.recorded, mProps.voiceBridge, nu)
      if (user.phoneUser.value) {
        if (meeting.hasUser(user.id)) {
          val userLeaving = meeting.removeUser(user.id)
          userLeaving foreach (u => outGW.send(new UserLeft(mProps.id, mProps.recorded, u)))
        }
      }

      stopRecordingVoiceConference()
    }
  }

  def handleUserMutedInVoiceConfMessage(msg: UserMutedInVoiceConfMessage) {
    meeting.getUserWithVoiceUserId(msg.voiceUserId) foreach { user =>
      val talking: Boolean = if (msg.muted) false else user.voiceUser.talking.value
      val nv = user.voiceUser.copy(muted = Muted(msg.muted), talking = Talking(talking))
      val nu = user.copy(voiceUser = nv)
      meeting.addUser(nu)

      log.info("User muted in voice conf. meetingId=" + mProps.id + " userId=" + nu.id + " user=" + nu)
      sendUserVoiceMutedMessage(mProps.id, mProps.recorded, mProps.voiceBridge, nu)
    }
  }

  def handleUserTalkingInVoiceConfMessage(msg: UserTalkingInVoiceConfMessage) {
    meeting.getUserWithVoiceUserId(msg.voiceUserId) foreach { user =>
      val nv = user.voiceUser.copy(talking = Talking(msg.talking))
      val nu = user.copy(voiceUser = nv)
      meeting.addUser(nu)
      //      println("Received voice talking=[" + msg.talking + "] wid=[" + msg.userId + "]" )
      sendUserVoiceTalkingMessage(mProps.id, mProps.recorded, mProps.voiceBridge, nu)
    }
  }

  def handleAssignPresenter(msg: AssignPresenter): Unit = {
    assignNewPresenter(msg.newPresenterId, msg.newPresenterName, msg.assignedBy)
  }

  def assignNewPresenter(newPresenterId: IntUserId, newPresenterName: Name, assignedBy: IntUserId) {
    // Stop poll if one is running as presenter left.
    handleStopPollRequest(StopPollRequest(mProps.id, assignedBy))

    if (meeting.hasUser(newPresenterId)) {

      meeting.getCurrentPresenter match {
        case Some(curPres) => {
          meeting.unbecomePresenter(curPres.id.value)
          sendUserStatusChangeMessage(mProps.id, mProps.recorded, curPres.id, false)
        }
        case None => // do nothing
      }

      meeting.getUser(newPresenterId) match {
        case Some(newPres) => {
          meeting.becomePresenter(newPres.id)
          meeting.setCurrentPresenterInfo(new Presenter(newPresenterId, newPresenterName, assignedBy))
          sendPresenterAssignedMessage(mProps.id, mProps.recorded, new Presenter(newPresenterId, newPresenterName, assignedBy))
          sendUserStatusChangeMessage(mProps.id, mProps.recorded, newPresenterId, true)
        }
        case None => // do nothing
      }

    }
  }
}
