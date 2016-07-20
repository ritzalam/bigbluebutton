package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core2x.bus.ReceivedJsonMessage

trait ReceivedJsonMessageHandler {
  def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit
}
