package org.bigbluebutton.core

import akka.actor.ActorSystem
import akka.event.Logging
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.api.json.{ IncomingEventBus2x, IncomingJsonMessage, IncomingJsonMessageBus, ReceivedJsonMessage }

class BigBlueButtonInGW(
    val system: ActorSystem,
    outGW: OutMessageGateway,
    eventBus2x: IncomingEventBus2x,
    incomingJsonMessageBus: IncomingJsonMessageBus) extends SystemConfiguration {

  val log = Logging(system, getClass)

  def handleReceivedJsonMessage(name: String, json: String): Unit = {
    val receivedJsonMessage = new ReceivedJsonMessage(name, json)
    incomingJsonMessageBus.publish(IncomingJsonMessage("incoming-json-message", receivedJsonMessage))
  }

}
