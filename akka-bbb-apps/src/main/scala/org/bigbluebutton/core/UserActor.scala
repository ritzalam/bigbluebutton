package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.api.{ EjectUserFromMeeting, ValidateAuthToken }
import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.domain.MeetingProperties

object UserActor {
  def props(
    mProps: MeetingProperties,
    eventBus: IncomingEventBus,
    outGW: OutMessageGateway): Props =
    Props(classOf[UserActor], mProps, eventBus, outGW)
}

class UserActor(
    val mProps: MeetingProperties,
    val eventBus: IncomingEventBus,
    val outGW: OutMessageGateway) extends Actor with ActorLogging {

  val handler = new UserActorMessageHandler

  def receive = {
    case msg: ValidateAuthToken => // TODO: Implement handler
    case msg: EjectUserFromMeeting => // TODO: Implement handler
  }
}