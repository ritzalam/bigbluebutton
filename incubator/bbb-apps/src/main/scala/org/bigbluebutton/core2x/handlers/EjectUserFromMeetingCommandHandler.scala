package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage.EjectUserFromMeeting
import org.bigbluebutton.core2x.api.OutGoingMessage.{ DisconnectUser2x, UserEjectedFromMeeting, UserLeft2x }
import org.bigbluebutton.core2x.domain.{ CanEjectUser, User3x }
import org.bigbluebutton.core2x.filters.DefaultAbilitiesFilter
import org.bigbluebutton.core2x.models.{ MeetingStateModel, Users3x }

trait EjectUserFromMeetingCommandHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handleEjectUserFromMeeting(msg: EjectUserFromMeeting) {
    def removeAndEject(user: User3x): Unit = {
      // remove user from list of users
      state.users.remove(user.id)
      // remove user from registered users to prevent re-joining
      state.registeredUsers.remove(msg.userId)

      // Send message to user that he has been ejected.
      outGW.send(new UserEjectedFromMeeting(state.props.id,
        state.props.recordingProp.recorded,
        msg.userId, msg.ejectedBy))
      // Tell system to disconnect user.
      outGW.send(new DisconnectUser2x(msg.meetingId, msg.userId))
      // Tell all others that user has left the meeting.
      outGW.send(new UserLeft2x(state.props.id,
        state.props.recordingProp.recorded,
        msg.userId))
    }

    for {
      user <- Users3x.findWithId(msg.userId, state.users.toVector)
    } yield removeAndEject(user)
  }
}

trait EjectUserFromMeetingCommandFilter extends EjectUserFromMeetingCommandHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  object DefaultAbilitiesFilter extends DefaultAbilitiesFilter
  val abilitiesFilter = DefaultAbilitiesFilter

  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting): Unit = {
    Users3x.findWithId(msg.ejectedBy, state.users.toVector) foreach { user =>

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
  abstract override def handleEjectUserFromMeeting(msg: EjectUserFromMeeting): Unit = {
    println("**** handleEjectUserFromMeeting ****")
  }
}