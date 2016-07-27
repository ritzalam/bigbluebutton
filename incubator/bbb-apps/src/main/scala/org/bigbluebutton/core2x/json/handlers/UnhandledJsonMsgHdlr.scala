package org.bigbluebutton.core2x.json.handlers

import org.bigbluebutton.core2x.RedisMsgHdlrActor
import org.bigbluebutton.core2x.json.{ IncomingEventBus2x, ReceivedJsonMessage }

trait UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    log.warning("Unhandled json message: " + msg.data)
  }
}
