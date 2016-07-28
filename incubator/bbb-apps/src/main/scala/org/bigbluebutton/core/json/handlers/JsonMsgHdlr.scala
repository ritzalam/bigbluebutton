package org.bigbluebutton.core.json.handlers

import org.bigbluebutton.core.json.ReceivedJsonMessage

trait JsonMsgHdlr {
  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit
}
