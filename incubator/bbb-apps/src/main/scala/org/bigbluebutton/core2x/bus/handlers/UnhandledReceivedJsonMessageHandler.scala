package org.bigbluebutton.core2x.bus.handlers

import akka.event.Logging
import org.bigbluebutton.core2x.bus.ReceivedJsonMessage

trait UnhandledReceivedJsonMessageHandler {

  def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    // TODO: Need to log
    println("Unhandled json message: " + msg.data)
  }
}
