package org.bigbluebutton.core2x.bus.handlers

import akka.event.Logging
import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.bus.{ IncomingEventBus2x, ReceivedJsonMessage }

trait UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    log.warning("Unhandled json message: " + msg.data)
  }
}
