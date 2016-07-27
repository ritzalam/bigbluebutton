package org.bigbluebutton.core2x.json.handlers

import org.bigbluebutton.core2x.json.ReceivedJsonMessage

trait JsonMsgHdlr {
  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit
}
