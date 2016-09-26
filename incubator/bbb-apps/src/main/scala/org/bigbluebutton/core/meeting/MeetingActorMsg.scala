package org.bigbluebutton.core.meeting

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.{ IncomingEventBus2x, OutMessageGateway, UserHandlers }
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.domain.MeetingProperties2x
import org.bigbluebutton.core.meeting.handlers._
import org.bigbluebutton.core.meeting.models.MeetingStateModel
import org.bigbluebutton.core.reguser.handlers.RegisterUserCommandMsgHdlr

import scala.concurrent.duration._

object MeetingActorInternal {
  def props(mProps: MeetingProperties2x,
    eventBus: IncomingEventBus2x,
    outGW: OutMessageGateway): Props =
    Props(classOf[MeetingActorInternal], mProps, eventBus, outGW)
}

// This actor is an internal audit actor for each meeting actor that
// periodically sends messages to the meeting actor
class MeetingActorInternal(val mProps: MeetingProperties2x,
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

object MeetingActorMsg {
  def props(
    props: MeetingProperties2x,
    bus: IncomingEventBus2x,
    outGW: OutMessageGateway,
    state: MeetingStateModel): Props =
    Props(classOf[MeetingActorMsg], props, bus, outGW, state)
}

class MeetingActorMsg(
  val props: MeetingProperties2x,
  val bus: IncomingEventBus2x,
  val outGW: OutMessageGateway,
  val state: MeetingStateModel) extends Actor with ActorLogging
    with DefaultInMsgHandler
    with RegisterSessionIdInMsgHdlr
    with AssignUserSessionTokenInMsgHdlr
    with RegisterUserCommandMsgHdlr
    with UserJoinMeetingRequestMsgHdlrFilter
    with EjectUserFromMeetingCommandMsgFilter {

  val userHandlers = new UserHandlers

  override def preStart(): Unit = {
    /** Subscribe to meeting and voice events. **/
    bus.subscribe(self, props.id.value)
    bus.subscribe(self, props.voiceConf.value)
    super.preStart()
  }

  override def postStop(): Unit = {
    bus.unsubscribe(self, props.id.value)
    bus.unsubscribe(self, props.voiceConf.value)
    super.postStop()
  }

  def receive = {
    case msg: RegisterUserInMessage => handle(msg)
    case msg: AssignUserSessionTokenInMsg2x => handle(msg)
    case msg: UserJoinMeetingInMessage => handle(msg)
    case msg: EjectUserFromMeetingInMsg => handle(msg)
    case msg: RegisterSessionIdInMsg => handle(msg)
  }

}
