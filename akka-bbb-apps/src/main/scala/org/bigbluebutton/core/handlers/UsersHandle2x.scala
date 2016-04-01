package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api.{ UserJoining, ValidateAuthToken, RegisterUser }
import org.bigbluebutton.core.domain.Muted
import org.bigbluebutton.core.models.{ Users2x, RegisteredUsers2x }
import org.bigbluebutton.core.{ OutMessageGateway, LiveMeeting }

trait UsersHandler2x extends UsersApp with UsersMessageSender {
  this: LiveMeeting =>

  val outGW: OutMessageGateway
  val registeredUsers2x: RegisteredUsers2x
  val users2x: Users2x

  def handleRegisterUser2x(msg: RegisterUser): Unit = {
    val regUser = RegisteredUsers2x.create(msg.userId, msg.extUserId, msg.name, msg.roles, msg.authToken)
    val rusers = meeting.addRegisteredUser(msg.authToken, regUser)
    sendUserRegisteredMessage(props.id, props.recorded, regUser)
  }

  def handleValidateAuthToken2x(msg: ValidateAuthToken): Unit = {
    registeredUsers2x.findWithToken(msg.token) match {
      case Some(u) =>
        sendValidateAuthTokenReplyMessage(props.id, msg.userId, msg.token, true, msg.correlationId)
      case None =>
        sendValidateAuthTokenReplyMessage(props.id, msg.userId, msg.token, false, msg.correlationId)
    }
  }

  def handleUserJoin2x(msg: UserJoining): Unit = {
    // Check if there is a registered user with token
    // Check if there is a user already in the list of users, if so, might be a reconnect
    // Compare sessionId, if sessionId is not same then this is a reconnect
    // Just update the sessionId and send join success
    val regUser = registeredUsers2x.findWithToken(msg.token)
    val users = users2x.toVector
    val u = Users2x.findWithId(msg.userId, users)

    regUser foreach { ru =>
      val voiceUser = initializeVoice(msg.userId, ru.name)
      val locked = getInitialLockStatus(ru.roles)
      val uvo = createNewUser(msg.userId, ru.extId, ru.name, ru.roles, voiceUser, getInitialLockStatus(ru.roles))
      sendUserJoinedMessage(props.id, props.recorded, uvo)
      sendMeetingStateMessage(props.id, props.recorded, uvo.id, meeting.getPermissions,
        Muted(meeting.isMeetingMuted))

      becomePresenterIfOnlyModerator(msg.userId, ru.name, ru.roles)
    }

    webUserJoined
    startRecordingIfAutoStart()
  }
}
