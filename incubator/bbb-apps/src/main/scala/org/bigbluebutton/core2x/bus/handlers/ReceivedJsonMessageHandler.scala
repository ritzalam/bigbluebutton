package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core2x.bus.ReceivedJsonMessage

trait ReceivedJsonMessageHandler {
  def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit
}
