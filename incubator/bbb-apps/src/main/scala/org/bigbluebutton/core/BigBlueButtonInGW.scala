package org.bigbluebutton.core

import org.bigbluebutton.core.bus._
import org.bigbluebutton.core.api._
import akka.actor.ActorSystem
import org.bigbluebutton.common.messages.IBigBlueButtonMessage
import org.bigbluebutton.messages._
import akka.event.Logging
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core2x.api.IncomingMessage.CreateMeetingRequestInMessage
import org.bigbluebutton.core2x.domain.{ Permissions => _, Role => _, _ }
import org.bigbluebutton.core2x.BigBlueButtonActor2x
import org.bigbluebutton.core2x.bus._

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
