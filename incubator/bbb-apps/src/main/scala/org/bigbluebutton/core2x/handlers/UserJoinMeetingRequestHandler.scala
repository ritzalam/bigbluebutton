package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.UserHandlers
import org.bigbluebutton.core2x.api.IncomingMsg.UserJoinMeetingInMessage
import org.bigbluebutton.core2x.api.OutGoingMsg.DisconnectUser2x
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsersModel }

trait UserJoinMeetingRequestHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway
  val userHandlers: UserHandlers

  def handleUserJoinMeetingRequestInMessage(msg: UserJoinMeetingInMessage): Unit = {

    // Check if there is a registered user with token
    // Check if there is a user already in the list of users, if so, might be a reconnect
    // Compare sessionId, if sessionId is not same then this is a reconnect
    // Just update the sessionId and send join success

    userHandlers.get(msg.senderId) foreach { handler => handler.handleUserJoinMeetingMessage(msg, state) }

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

trait UserJoinMeetingRequestHandlerFilter extends UserJoinMeetingRequestHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  abstract override def handleUserJoinMeetingRequestInMessage(msg: UserJoinMeetingInMessage): Unit = {
    RegisteredUsersModel.findWithToken(msg.sessionToken, state.registeredUsersModel.toVector) match {
      case Some(u) =>
        super.handleUserJoinMeetingRequestInMessage(msg)
      case None =>
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
    }
  }
}
