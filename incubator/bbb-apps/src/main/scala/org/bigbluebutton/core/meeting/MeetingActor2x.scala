package org.bigbluebutton.core.meeting

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.{ OutMessageGateway, UserHandlers }
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.domain.MeetingProperties2x
import org.bigbluebutton.core.api.json.IncomingEventBus2x
import org.bigbluebutton.core.meeting.handlers._
import org.bigbluebutton.core.meeting.models.MeetingStateModel

import scala.concurrent.duration._

object MeetingActorInternal2x {
  def props(mProps: MeetingProperties2x,
    eventBus: IncomingEventBus2x,
    outGW: OutMessageGateway): Props =
    Props(classOf[MeetingActorInternal2x], mProps, eventBus, outGW)
}

// This actor is an internal audit actor for each meeting actor that
// periodically sends messages to the meeting actor
class MeetingActorInternal2x(val mProps: MeetingProperties2x,
  val eventBus: IncomingEventBus2x, val outGW: OutMessageGateway)
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
    bus: IncomingEventBus2x,
    outGW: OutMessageGateway,
    state: MeetingStateModel): Props =
    Props(classOf[MeetingActor2x], props, bus, outGW, state)
}

class MeetingActor2x(
  val props: MeetingProperties2x,
  val bus: IncomingEventBus2x,
  val outGW: OutMessageGateway,
  val state: MeetingStateModel) extends Actor with ActorLogging
    with RegisterSessionIdInMsgHandler
    with ValidateAuthTokenCommandFilter
    with RegisterUserCommandHandler
    with UserJoinMeetingRequestHandlerFilter
    with EjectUserFromMeetingCommandFilter {

  val userHandlers = new UserHandlers

  /** Subscribe to meeting and voice events. **/
  bus.subscribe(self, props.id.value)
  bus.subscribe(self, props.voiceConf.value)

  def receive = {
    case msg: RegisterUserInMessage =>
      log.debug("Handling RegisterUserRequestInMessage")
      handleRegisterUser2x(msg)
    case msg: ValidateAuthTokenInMessage =>
      log.debug("Handling ValidateAuthTokenRequestInMessage")
      handleValidateAuthToken2x(msg)
    case msg: UserJoinMeetingInMessage =>
      log.debug("Handling NewUserPresence2x")
      handleUserJoinMeetingRequestInMessage(msg)
    case msg: EjectUserFromMeetingInMsg =>
      log.debug("Handling EjectUserFromMeeting")
      handleEjectUserFromMeeting(msg)
    case msg: RegisterSessionIdInMsg =>
      handleRegisterSessionIdInMsg(msg)
  }

}
