package org.bigbluebutton.core2x.handlers.user

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg._
import org.bigbluebutton.core2x.domain.{ User$, _ }
import org.bigbluebutton.core2x.models._

class UserActorMessageHandler(
  val user: RegisteredUser2x,
  val outGW: OutMessageGateway)
    extends SystemConfiguration
    with ValidateAuthTokenHandler
    with UserJoinMeetingMessageHandler {

  val userState: UserState = new UserState(user)

  def handleEjectUserFromMeeting(msg: EjectUserFromMeetingInMsg, meeting: MeetingStateModel): Unit = {

  }

  def handleUserLeave2xCommand(msg: UserLeaveMeetingInMessage, meeting: MeetingStateModel): Unit = {
    UsersModel.findWithId(msg.senderId, meeting.usersModel.toVector) match {
      case Some(user) =>
        // Find presence associated with this session
        val presence = User.findWithClientId(user.client, msg.presenceId)

      // TODO: Send reconnecting message
      case None =>

    }
  }

  def handleViewWebCamRequest2x(msg: UserViewWebCamRequestInMsg, meeting: MeetingStateModel): Unit = {
    def send(tokens: Set[String]): Unit = {
      if (tokens.contains(msg.token)) {
        // send media info
      }
    }

    for {
      user <- UsersModel.findWithId(msg.senderId, meeting.usersModel.toVector)
    } yield send(userState.get.tokens)

  }

  def handleShareWebCamRequest2x(msg: UserShareWebCamRequestInMsg, meeting: MeetingStateModel): Unit = {
    def send(): Unit = {

    }

    for {
      user <- UsersModel.findWithId(msg.senderId, meeting.usersModel.toVector)
      presence <- User.findWithClientId(user.client, msg.presenceId)
    } yield send()

  }

  def handleUserShareWebCam2x(msg: UserStartedPublishWebCamInMsg, meeting: MeetingStateModel): Unit = {
    def send(): Unit = {

    }

    for {
      user <- UsersModel.findWithId(msg.senderId, meeting.usersModel.toVector)
      presence <- User.findWithClientId(user.client, msg.presenceId)
    } yield send()

  }

  def handleUserUnShareWebCam2x(msg: UserStoppedPublishWebCamInMsg, meeting: MeetingStateModel): Unit = {
    def send(): Unit = {

    }

    for {
      user <- UsersModel.findWithId(msg.senderId, meeting.usersModel.toVector)
      presence <- User.findWithClientId(user.client, msg.presenceId)
    } yield send()
  }
}
