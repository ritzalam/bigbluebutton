package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg.EjectUserFromMeetingInMessage
import org.bigbluebutton.core2x.api.OutGoingMsg.{ DisconnectUser2x, UserEjectedFromMeetingEventOutMsg, UserLeftEventOutMsg }
import org.bigbluebutton.core2x.domain.{ CanEjectUser, User3x }
import org.bigbluebutton.core2x.filters.DefaultAbilitiesFilter
import org.bigbluebutton.core2x.models.{ MeetingStateModel, UsersModel }

trait EjectUserFromMeetingCommandHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handleEjectUserFromMeeting(msg: EjectUserFromMeetingInMessage) {
    def removeAndEject(user: User3x): Unit = {
      // remove user from list of users
      state.usersModel.remove(user.id)
      // remove user from registered users to prevent re-joining
      state.registeredUsersModel.remove(msg.userId)

      // Send message to user that he has been ejected.
      outGW.send(new UserEjectedFromMeetingEventOutMsg(state.props.id,
        state.props.recordingProp.recorded,
        msg.userId, msg.ejectedBy))
      // Tell system to disconnect user.
      outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
      // Tell all others that user has left the meeting.
      outGW.send(new UserLeftEventOutMsg(state.props.id,
        state.props.recordingProp.recorded,
        msg.userId))
    }

    for {
      user <- UsersModel.findWithId(msg.userId, state.usersModel.toVector)
    } yield removeAndEject(user)
  }
}

trait EjectUserFromMeetingCommandFilter extends EjectUserFromMeetingCommandHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val abilitiesFilter = DefaultAbilitiesFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeetingInMessage): Unit = {
    UsersModel.findWithId(msg.ejectedBy, state.usersModel.toVector) foreach { user =>

      val abilities = abilitiesFilter.calcEffectiveAbilities(
        user.roles,
        user.permissions,
        state.abilities.get.removed)

      if (abilitiesFilter.can(CanEjectUser, abilities)) {
        super.handleEjectUserFromMeeting(msg)
      } else {
        outGW.send(new DisconnectUser2x(msg.meetingId, msg.ejectedBy))
      }
    }
  }
}

trait EjectUserFromMeetingCommandLogFilter extends EjectUserFromMeetingCommandHandler {
  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeetingInMessage): Unit = {
    println("**** handleEjectUserFromMeeting ****")
  }
}