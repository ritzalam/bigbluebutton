package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.api.{ EjectUserFromMeeting, ValidateAuthToken }
import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.domain.{ MeetingActorRef, MeetingProperties }

object UserActor {
  def props(
    props: MeetingProperties,
    bus: IncomingEventBus,
    outGW: OutMessageGateway): Props =
    Props(classOf[UserActor], props, bus, outGW)
}

class UserActor(
    val props: MeetingProperties,
    val eventBus: IncomingEventBus,
    val outGW: OutMessageGateway) extends Actor with ActorLogging {

  val handler = new UserActorMessageHandler

  def receive = {
    case msg: ValidateAuthToken => handleValidateAuthToken(msg)
    case msg: EjectUserFromMeeting => // TODO: Implement handler
  }

  def handleValidateAuthToken(msg: ValidateAuthToken): Unit = {

  }
}