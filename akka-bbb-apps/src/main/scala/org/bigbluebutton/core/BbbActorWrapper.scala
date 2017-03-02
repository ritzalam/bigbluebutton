package org.bigbluebutton.core

import akka.actor.{ ActorContext, ActorSystem }
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.bus.{ IncomingEventBus, IncomingEventBus2x }

/**
 * Created by ritz on 2017-03-02.
 */
object BbbActorWrapper {
  def apply(eventBus: IncomingEventBus,
    outGW: OutMessageGateway, outGW2x: OutMessageGateway2x,
    eventBus2x: IncomingEventBus2x)(implicit context: ActorSystem) =
    new BbbActorWrapper(eventBus, outGW, outGW2x, eventBus2x)(context)
}

class BbbActorWrapper(eventBus: IncomingEventBus,
  outGW: OutMessageGateway, outGW2x: OutMessageGateway2x,
  eventBus2x: IncomingEventBus2x)(implicit context: ActorSystem)
    extends SystemConfiguration {

  val bbbActor = context.actorOf(BigBlueButtonActor.props(context, eventBus, outGW, outGW2x), "bigbluebutton-actor")
  eventBus.subscribe(bbbActor, "meeting-manager")
  eventBus2x.subscribe(bbbActor, meetingManagerChannel)
}