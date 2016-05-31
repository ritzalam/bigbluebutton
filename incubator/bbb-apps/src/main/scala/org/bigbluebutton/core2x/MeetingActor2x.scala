package org.bigbluebutton.core2x

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core2x.domain.MeetingProperties2x
import org.bigbluebutton.core2x.filters.UsersHandlerFilter
import org.bigbluebutton.core2x.models.MeetingStateModel
import org.bigbluebutton.core2x.api.IncomingMessage._
import org.bigbluebutton.core2x.handlers._

import scala.concurrent.duration._

object MeetingActorInternal2x {
  def props(mProps: MeetingProperties2x,
    eventBus: IncomingEventBus,
    outGW: OutMessageGateway): Props =
    Props(classOf[MeetingActorInternal2x], mProps, eventBus, outGW)
}

// This actor is an internal audit actor for each meeting actor that
// periodically sends messages to the meeting actor
class MeetingActorInternal2x(val mProps: MeetingProperties2x,
  val eventBus: IncomingEventBus, val outGW: OutMessageGateway)
    extends Actor with ActorLogging {

  import context.dispatcher
  context.system.scheduler.schedule(2 seconds, 30 seconds, self, "MonitorNumberOfWebUsers")

  // Query to get voice conference users
  //outGW.send(new GetUsersInVoiceConference(mProps.meetingID, mProps.recorded, mProps.voiceBridge))

  if (mProps.isBreakout) {
    // This is a breakout room. Inform our parent meeting that we have been successfully created.
    //eventBus.publish(BigBlueButtonEvent(
    // mProps.externalMeetingID,
    // BreakoutRoomCreated(mProps.externalMeetingID, mProps.meetingID)))
  }

  def receive = {
    case "MonitorNumberOfWebUsers" => handleMonitorNumberOfWebUsers()
  }

  def handleMonitorNumberOfWebUsers() {
    //eventBus.publish(BigBlueButtonEvent(mProps.meetingID, MonitorNumberOfUsers(mProps.meetingID)))

    // Trigger updating users of time remaining on meeting.
    //eventBus.publish(BigBlueButtonEvent(mProps.meetingID, SendTimeRemainingUpdate(mProps.meetingID)))

    //if (mProps.isBreakout) {
    // This is a breakout room. Update the main meeting with list of users in this breakout room.
    //  eventBus.publish(BigBlueButtonEvent(mProps.meetingID, SendBreakoutUsersUpdate(mProps.meetingID)))
    //}

  }
}

object MeetingActor2x {
  def props(
    props: MeetingProperties2x,
    bus: IncomingEventBus,
    outGW: OutMessageGateway,
    state: MeetingStateModel): Props =
    Props(classOf[MeetingActor2x], props, bus, outGW, state)
}

class MeetingActor2x(
  val props: MeetingProperties2x,
  val bus: IncomingEventBus,
  val outGW: OutMessageGateway,
  val state: MeetingStateModel) extends Actor with ActorLogging
    with ValidateAuthTokenCommandFilter
    with RegisterUserCommandHandler
    with UserJoinedCommandHandlerFilter
    with EjectUserFromMeetingCommandFilter {

  val userHandlers = new UserHandlers

  def receive = {
    case msg: RegisterUser2xCommand => handleRegisterUser2x(msg)
    case msg: ValidateAuthToken => handleValidateAuthToken2x(msg)
    case msg: NewUserPresence2x => handleUserJoinWeb2x(msg)
    case msg: EjectUserFromMeeting => handleEjectUserFromMeeting(msg)
  }

}
