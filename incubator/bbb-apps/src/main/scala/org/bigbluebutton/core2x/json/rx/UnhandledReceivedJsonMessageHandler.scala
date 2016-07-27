package org.bigbluebutton.core2x.json.rx

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.json.{ IncomingEventBus2x, ReceivedJsonMessage }

trait UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    log.warning("Unhandled json message: " + msg.data)
  }
}
