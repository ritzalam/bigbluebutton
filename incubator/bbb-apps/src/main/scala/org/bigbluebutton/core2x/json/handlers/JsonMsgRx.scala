package org.bigbluebutton.core2x.json.handlers

import org.bigbluebutton.core2x.json.ReceivedJsonMessage

trait JsonMsgRx {
  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit
}
