package org.bigbluebutton.core2x

import akka.actor.ActorSystem
import akka.event.Logging
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.api._
import org.bigbluebutton.core2x.domain.{ Permissions => _, Role => _ }
import org.bigbluebutton.core2x.json._

class BigBlueButtonInGW(
    val system: ActorSystem,
    outGW: OutMessageGateway,
    eventBus2x: IncomingEventBus2x,
    incomingJsonMessageBus: IncomingJsonMessageBus) extends IBigBlueButtonInGW with SystemConfiguration {

  val log = Logging(system, getClass)

  def handleReceivedJsonMessage(name: String, json: String): Unit = {
    println("INGW: \n" + json)
    val receivedJsonMessage = new ReceivedJsonMessage(name, json)
    incomingJsonMessageBus.publish(IncomingJsonMessage("incoming-json-message", receivedJsonMessage))
  }

}
