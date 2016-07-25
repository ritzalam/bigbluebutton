package org.bigbluebutton.core2x.handlers.user

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg.UserJoinMeetingInMessage
import org.bigbluebutton.core2x.api.OutGoingMsg.{ PresenterAssignedEventOutMsg, UserJoinedEvent2x }
import org.bigbluebutton.core2x.domain.{ Presenter, PresenterRole, User }
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsersModel, UsersModel }

trait UserJoinMeetingMessageHandler {
  val outGW: OutMessageGateway

  def handleUserJoinMeetingMessage(msg: UserJoinMeetingInMessage, meeting: MeetingStateModel): Unit = {
    def becomePresenterIfNeeded(user: User): Unit = {
      // Become presenter if only moderator in meeting
      if (user.isModerator && !UsersModel.hasPresenter(meeting.usersModel.toVector)) {
        val u = User.add(user, PresenterRole)
        meeting.usersModel.save(u)
        // Send presenter assigned message
        val newPresenter = new Presenter(u.id, u.name, u.id)
        outGW.send(new PresenterAssignedEventOutMsg(msg.meetingId, meeting.props.recordingProp.recorded, newPresenter))
      }
    }

    def process(user: User): Unit = {
      meeting.usersModel.save(user)
      outGW.send(new UserJoinedEvent2x(msg.meetingId, meeting.props.recordingProp.recorded, user))
      becomePresenterIfNeeded(user)
    }

    UsersModel.findWithId(msg.senderId, meeting.usersModel.toVector) match {
      case Some(user) =>
        // Find presence associated with this session
        val presence = User.findWithClientId(user.client, msg.presenceId)

      // TODO: Send reconnecting message
      case None =>
        for {
          ru <- RegisteredUsersModel.findWithToken(msg.token, meeting.registeredUsersModel.toVector)
          u = User.create(msg.senderId, ru.extId, ru.name, ru.roles)
          presence = User.create(msg.presenceId, msg.userAgent)
          user = User.add(u, presence)
        } yield process(user)
    }

  }
}
