package org.bigbluebutton.core2x.handlers.user

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage._
import org.bigbluebutton.core2x.api.OutGoingMessage._
import org.bigbluebutton.core2x.domain.{ User3x, _ }
import org.bigbluebutton.core2x.models._

class UserActorMessageHandler(
  val user: RegisteredUser2x,
  val outGW: OutMessageGateway)
    extends SystemConfiguration
    with ValidateAuthTokenHandler
    with UserJoinedHandler {

  val userState: UserState = new UserState(user)

  def handleEjectUserFromMeeting(msg: EjectUserFromMeeting, meeting: MeetingStateModel): Unit = {

  }

  def handleUserLeave2xCommand(msg: UserLeave2xCommand, meeting: MeetingStateModel): Unit = {
    Users3x.findWithId(msg.userId, meeting.users.toVector) match {
      case Some(user) =>
        // Find presence associated with this session
        val presence = User3x.findWithPresenceId(user.presence, msg.presenceId)

      // TODO: Send reconnecting message
      case None =>

    }
  }

  def handleViewWebCamRequest2x(msg: ViewWebCamRequest2x, meeting: MeetingStateModel): Unit = {
    def send(tokens: Set[String]): Unit = {
      if (tokens.contains(msg.token)) {
        // send media info
      }
    }

    for {
      user <- Users3x.findWithId(msg.userId, meeting.users.toVector)
    } yield send(userState.get.tokens)

  }

  def handleShareWebCamRequest2x(msg: ShareWebCamRequest2x, meeting: MeetingStateModel): Unit = {
    def send(): Unit = {

    }

    for {
      user <- Users3x.findWithId(msg.userId, meeting.users.toVector)
      presence <- User3x.findWithPresenceId(user.presence, msg.presenceId)
    } yield send()

  }

  def handleUserShareWebCam2x(msg: UserShareWebCam2x, meeting: MeetingStateModel): Unit = {
    def send(): Unit = {

    }

    for {
      user <- Users3x.findWithId(msg.userId, meeting.users.toVector)
      presence <- User3x.findWithPresenceId(user.presence, msg.presenceId)
    } yield send()

  }

  def handleUserUnShareWebCam2x(msg: UserUnShareWebCam2x, meeting: MeetingStateModel): Unit = {
    def send(): Unit = {

    }

    for {
      user <- Users3x.findWithId(msg.userId, meeting.users.toVector)
      presence <- User3x.findWithPresenceId(user.presence, msg.presenceId)
    } yield send()
  }
}
