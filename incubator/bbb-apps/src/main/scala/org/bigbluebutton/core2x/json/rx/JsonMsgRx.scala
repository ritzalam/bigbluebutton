package org.bigbluebutton.core2x.json.rx

import org.bigbluebutton.core2x.json.ReceivedJsonMessage

trait JsonMsgRx {
  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit
}
