package org.bigbluebutton.core

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.core.api.{EjectUserFromMeeting, ValidateAuthToken}
import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.domain.{MeetingActorRef, MeetingProperties}

object UserActor {
  def props(
           meetingActorRef: MeetingActorRef,
           props: MeetingProperties,
    eventBus: IncomingEventBus,
    outGW: OutMessageGateway): Props =
    Props(classOf[UserActor], meetingActorRef, props, eventBus, outGW)
}

class UserActor(
                 meetingActorRef: MeetingActorRef,
    val mProps: MeetingProperties,
    val eventBus: IncomingEventBus,
    val outGW: OutMessageGateway) extends Actor with ActorLogging {

  val handler = new UserActorMessageHandler

  def receive = {
    case msg: ValidateAuthToken => // TODO: Implement handler
    case msg: EjectUserFromMeeting => // TODO: Implement handler
  }
}