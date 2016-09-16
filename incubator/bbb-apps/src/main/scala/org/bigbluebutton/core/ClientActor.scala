package org.bigbluebutton.core

import akka.actor.{ Actor, ActorContext, ActorLogging, Props }
import org.bigbluebutton.core.api.json.{ IncomingEventBus2x, OutgoingEventBus }

object ClientActor {
  def props(incomingEventBus2x: IncomingEventBus2x, outgoingEventBus: OutgoingEventBus): Props =
    Props(classOf[ClientActor], incomingEventBus2x, outgoingEventBus)
}

class ClientActor(incomingEventBus2x: IncomingEventBus2x, outgoingEventBus: OutgoingEventBus)
    extends Actor with ActorLogging {

  def receive = {
    case _ => // do nothing for now.
  }
}
