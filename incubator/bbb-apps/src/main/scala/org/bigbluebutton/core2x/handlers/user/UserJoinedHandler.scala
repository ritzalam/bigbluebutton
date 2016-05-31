package org.bigbluebutton.core2x.handlers.user

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage.NewUserPresence2x
import org.bigbluebutton.core2x.api.OutGoingMessage.UserJoinedEvent2x
import org.bigbluebutton.core2x.domain.{ PresenterRole, User3x }
import org.bigbluebutton.core2x.models.{ MeetingStateModel, RegisteredUsers2x, Users3x }

trait UserJoinedHandler {
  val outGW: OutMessageGateway

  def handleUserJoinWeb2x(msg: NewUserPresence2x, meeting: MeetingStateModel): Unit = {
    def becomePresenter(user: User3x): Unit = {
      // TODO: Become presenter if only moderator in meeting
      if (user.isModerator && !Users3x.hasPresenter(meeting.users.toVector)) {
        val u = User3x.add(user, PresenterRole)
        meeting.users.save(u)
        // Send presenter assigned message
      }
    }

    def process(user: User3x): Unit = {
      meeting.users.save(user)
      outGW.send(new UserJoinedEvent2x(msg.meetingId, meeting.props.recordingProp.recorded, user))
      becomePresenter(user)
    }

    Users3x.findWithId(msg.userId, meeting.users.toVector) match {
      case Some(user) =>
        // Find presence associated with this session
        val presence = User3x.findWithPresenceId(user.presence, msg.presenceId)

      // TODO: Send reconnecting message
      case None =>
        for {
          ru <- RegisteredUsers2x.findWithToken(msg.token, meeting.registeredUsers.toVector)
          u = User3x.create(msg.userId, ru.extId, ru.name, ru.roles)
          presence = User3x.create(msg.presenceId, msg.userAgent)
          user = User3x.add(u, presence)
        } yield process(user)
    }

  }
}
