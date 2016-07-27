package org.bigbluebutton.core2x.json.rx

import org.bigbluebutton.core2x.json.ReceivedJsonMessage

trait ReceivedJsonMessageHandler {
  def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit
}
