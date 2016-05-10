package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.{ NewUserPresence2x, ValidateAuthToken }
import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.domain.{ User3x, _ }
import org.bigbluebutton.core.models.{ MeetingState, Users3x }

class UserActorMessageHandler(id: IntUserId, extId: ExtUserId,
    props: MeetingProperties, bus: IncomingEventBus, outGW: OutMessageGateway) {

  val userState = new UserState(id, extId)

  def handleValidateAuthToken2x(msg: ValidateAuthToken, meeting: MeetingState): MeetingState = {
    meeting.registeredUsers.findWithToken(msg.token) match {
      case Some(u) => meeting
      //        sender.sendValidateAuthTokenReplyMessage(props.id, msg.userId, msg.token, true, msg.correlationId)
      case None => meeting
      //        sender.sendValidateAuthTokenReplyMessage(props.id, msg.userId, msg.token, false, msg.correlationId)
      // outGW.send()
    }
  }

  def handleUserJoinWeb2x(msg: NewUserPresence2x): Unit = {

    def createUser(ru: RegisteredUser2x): User3x = {
      Users3x.create(msg.userId, ru.extId, ru.name, msg.sessionId, ru.roles)
    }

    // Check if there is a registered user with token
    // Check if there is a user already in the list of users, if so, might be a reconnect
    // Compare sessionId, if sessionId is not same then this is a reconnect
    // Just update the sessionId and send join success

    meeting.state.users.findWithId(msg.userId) match {
      case Some(user) =>
      // Update just the session id as this is a reconnect.
      //val u = User2x.updateSessionId(user, msg.sessionId, msg.presenceId)
      //meeting.users.save(u)
      case None =>
        meeting.state.registeredUsers.findWithToken(msg.token) foreach { ru =>
          val uvo = createUser(ru)
          val presence = User3x.create(msg.presenceId, msg.userAgent)
          val user = User3x.add(uvo, presence)
          meeting.state.users.save(user)

          sender.sendUserJoinedMessage(meeting.props.id, meeting.props.recorded, uvo)

          // TODO: Become presenter if only moderator in meeting
          becomePresenterIfOnlyModerator(msg.userId, ru.name, ru.roles)

          // TODO: Keep track if there are still web users in the meeting.
          //          if (Users2x.numberOfWebUsers(meeting.state.users.toVector) > 0) {
          //            meeting.resetLastWebUserLeftOn()
          //          }

          // TODO: Start recording when first user joins meeting
          //          if (needToStartRecording(meeting)) {
          //            meeting.recordingStarted()
          //     sender.send(new RecordingStatusChanged(props.id, props.recorded, IntUserId("system"), meeting.isRecording))
          //          }
        }
    }

  }
}
