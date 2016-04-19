package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api.{ EjectUserFromMeeting, RegisterUser, UserJoining, ValidateAuthToken }
import org.bigbluebutton.core.models.{ Meeting2x, RegisteredUsers2x, Users2x }
import org.bigbluebutton.core.OutMessageGateway

trait UsersHandler2x {

  val meeting: Meeting2x
  val outGW: OutMessageGateway
  val sender = new UsersMessageSender(outGW)

  def handleRegisterUser2x(msg: RegisterUser): Unit = {
    val regUser = RegisteredUsers2x.create(msg.userId, msg.extUserId, msg.name, msg.roles, msg.authToken)
    val _ = meeting.regUsers2x.add(regUser)
    sender.sendUserRegisteredMessage(meeting.props.id, meeting.props.recorded, regUser)
  }

  def handleValidateAuthToken2x(msg: ValidateAuthToken): Unit = {
    meeting.regUsers2x.findWithToken(msg.token) match {
      case Some(u) =>
        sender.sendValidateAuthTokenReplyMessage(meeting.props.id, msg.userId, msg.token, true, msg.correlationId)
      case None =>
        sender.sendValidateAuthTokenReplyMessage(meeting.props.id, msg.userId, msg.token, false, msg.correlationId)
    }
  }

  def handleUserJoin2x(msg: UserJoining): Unit = {
    // Check if there is a registered user with token
    // Check if there is a user already in the list of users, if so, might be a reconnect
    // Compare sessionId, if sessionId is not same then this is a reconnect
    // Just update the sessionId and send join success
    val regUser = meeting.regUsers2x.findWithToken(msg.token)
    val users = meeting.users2x.toVector
    val u = Users2x.findWithId(msg.userId, users)

    regUser foreach { ru =>
      //val voiceUser = initializeVoice(msg.userId, ru.name)
      //      val locked = meeting.getInitialLockStatus(ru.roles)
      //      val uvo = createNewUser(msg.userId, ru.extId, ru.name, ru.roles, voiceUser, meeting.getInitialLockStatus(ru.roles))
      //      sender.sendUserJoinedMessage(meeting.props.id, meeting.props.recorded, uvo)
      //      sender.sendMeetingStateMessage(meeting.props.id, meeting.props.recorded, uvo.id, meeting.getPermissions,
      //        Muted(meeting.isMeetingMuted))

      //      becomePresenterIfOnlyModerator(msg.userId, ru.name, ru.roles)
    }
    //
    //    webUserJoined
    //    startRecordingIfAutoStart()
  }

  def handleEjectUserFromMeeting(msg: EjectUserFromMeeting): Unit = {

  }

  def handleUserJoin(msg: UserJoining): Unit = {

  }
}
