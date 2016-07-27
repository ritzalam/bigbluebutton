package org.bigbluebutton.core2x.json.handlers

import org.bigbluebutton.core2x.json.ReceivedJsonMessage

trait ReceivedJsonMessageHandler {
  def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit
}
