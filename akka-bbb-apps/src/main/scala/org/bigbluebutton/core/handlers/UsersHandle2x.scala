package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api.{ UserJoining, ValidateAuthToken, RegisterUser }
import org.bigbluebutton.core.domain.Muted
import org.bigbluebutton.core.models.RegisteredUsers2x
import org.bigbluebutton.core.{ OutMessageGateway, LiveMeeting }

trait UsersHandler2x extends UsersApp with UsersMessageSender {
  this: LiveMeeting =>

  val outGW: OutMessageGateway
  val registeredUsers: RegisteredUsers2x

  def handleRegisterUser2x(msg: RegisterUser): Unit = {
    val regUser = RegisteredUsers2x.create(msg.userId, msg.extUserId, msg.name, msg.roles, msg.authToken)
    val rusers = meeting.addRegisteredUser(msg.authToken, regUser)
    sendUserRegisteredMessage(props.id, props.recorded, regUser)
  }

  def handleValidateAuthToken2x(msg: ValidateAuthToken): Unit = {
    meeting.findWithToken(msg.token) match {
      case Some(u) =>
        sendValidateAuthTokenReplyMessage(props.id, msg.userId, msg.token, true, msg.correlationId)
      case None =>
        sendValidateAuthTokenReplyMessage(props.id, msg.userId, msg.token, false, msg.correlationId)
    }
  }

  def handleUserJoin2x(msg: UserJoining): Unit = {
    val regUser = registeredUsers.findWithToken(msg.token)
    val webUser = meeting.getUser(msg.userId)
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
      val locked = getInitialLockStatus(ru.roles)
      val uvo = createNewUser(msg.userId, ru.extId, ru.name, ru.roles, voiceUser, getInitialLockStatus(ru.roles))

      log.info("User joined meeting. metingId=" + props.id + " userId=" + uvo.id + " user=" + uvo)

      sendUserJoinedMessage(props.id, props.recorded, uvo)
      sendMeetingStateMessage(props.id, props.recorded, uvo.id, meeting.getPermissions,
        Muted(meeting.isMeetingMuted))

      becomePresenterIfOnlyModerator(msg.userId, ru.name, ru.roles)
    }

    webUserJoined
    startRecordingIfAutoStart()
  }
}
