package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.api.json.ReceivedJsonMessage

trait JsonMsgHdlr {
  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit
}
