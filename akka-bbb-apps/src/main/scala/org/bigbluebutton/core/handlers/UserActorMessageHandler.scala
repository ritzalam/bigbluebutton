package org.bigbluebutton.core.handlers

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.{ MeetingState => _, _ }
import org.bigbluebutton.core.domain.{ User3x, _ }
import org.bigbluebutton.core.models._

class UserActorMessageHandler(
    user: RegisteredUser2x,
    props: MeetingProperties2x,
    outGW: OutMessageGateway) extends SystemConfiguration {

  private val userState: UserState = new UserState(user)

  def handleValidateAuthToken2x(msg: ValidateAuthToken, meeting: MeetingState): Unit = {
    def sendResponse(user: RegisteredUser2x): Unit = {
      // TODO: Send response with user status
      outGW.send(new ValidateAuthTokenReply2x(msg.meetingId, msg.userId, msg.token, true))
    }

    for {
      user <- meeting.registeredUsers.findWithToken(msg.token)
    } yield sendResponse(user)
  }

  def handleUserJoinWeb2x(msg: NewUserPresence2x, meeting: MeetingState): Unit = {
    // Check if there is a registered user with token
    // Check if there is a user already in the list of users, if so, might be a reconnect
    // Compare sessionId, if sessionId is not same then this is a reconnect
    // Just update the sessionId and send join success

    meeting.users.findWithId(msg.userId) match {
      case Some(user) =>
        // Find presence associated with this session
        val presence = User3x.findWithPresenceId(user.presence, msg.presenceId)

      // TODO: Send reconnecting message
      case None =>
        meeting.registeredUsers.findWithToken(msg.token) foreach { ru =>
          //  val uvo = userState.get
          val u = Users3x.create(msg.userId, ru.extId, ru.name, ru.roles)
          val presence = User3x.create(msg.presenceId, msg.userAgent)
          val user = User3x.add(u, presence)
          meeting.users.save(user)

          //    sender.sendUserJoinedMessage(props.id, meeting.recorded, uvo)

          // TODO: Become presenter if only moderator in meeting
          //    becomePresenterIfOnlyModerator(msg.userId, ru.name, ru.roles)

        }
    }

  }

  def handleUserLeftWeb2x(msg: UserPresenceLeft2x, meeting: MeetingState): Unit = {
    meeting.users.findWithId(msg.userId) match {
      case Some(user) =>
        // Find presence associated with this session
        val presence = User3x.findWithPresenceId(user.presence, msg.presenceId)

      // TODO: Send reconnecting message
      case None =>

    }
  }

  def handleViewWebCamRequest2x(msg: ViewWebCamRequest2x, meeting: MeetingState): Unit = {
    def send(tokens: Set[String]): Unit = {
      if (tokens.contains(msg.token)) {
        // send media info
      }
    }

    for {
      user <- meeting.users.findWithId(msg.userId)
    } yield send(userState.get.tokens)

  }

  def handleShareWebCamRequest2x(msg: ShareWebCamRequest2x, meeting: MeetingState): Unit = {
    def send(): Unit = {

    }

    for {
      user <- meeting.users.findWithId(msg.userId)
      presence <- User3x.findWithPresenceId(user.presence, msg.presenceId)
    } yield send()

  }

  def handleUserShareWebCam2x(msg: UserShareWebCam2x, meeting: MeetingState): Unit = {
    def send(): Unit = {

    }

    for {
      user <- meeting.users.findWithId(msg.userId)
      presence <- User3x.findWithPresenceId(user.presence, msg.presenceId)
    } yield send()

  }

  def handleUserUnShareWebCam2x(msg: UserUnShareWebCam2x, meeting: MeetingState): Unit = {
    def send(): Unit = {

    }

    for {
      user <- meeting.users.findWithId(msg.userId)
      presence <- User3x.findWithPresenceId(user.presence, msg.presenceId)
    } yield send()
  }
}
