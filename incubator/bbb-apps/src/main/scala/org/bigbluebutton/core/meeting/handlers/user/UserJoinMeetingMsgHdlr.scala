package org.bigbluebutton.core.meeting.handlers.user

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg.UserJoinMeetingInMessage
import org.bigbluebutton.core.api.OutGoingMsg.{ PresenterAssignedEventOutMsg, UserJoinedEvent2x }
import org.bigbluebutton.core.domain.{ Presenter, PresenterRole, User }
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, RegisteredUsersModel, UsersModel }

trait UserJoinMeetingMsgHdlr {
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
        val client = User.findWithClientId(user.client, msg.presenceId)

      // TODO: Send reconnecting message
      case None =>
        for {
          ru <- RegisteredUsersModel.findWithToken(msg.sessionToken, meeting.registeredUsersModel.toVector)
          u = User.create(msg.senderId, ru.extId, ru.name, ru.roles)
          presence = User.create(msg.presenceId, u.id, msg.sessionToken, msg.userAgent)
          user = User.add(u, presence)
        } yield process(user)
    }

  }
}
