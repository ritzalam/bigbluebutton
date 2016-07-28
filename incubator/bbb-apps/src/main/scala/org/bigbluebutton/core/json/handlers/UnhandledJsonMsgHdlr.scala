package org.bigbluebutton.core.json.handlers

import org.bigbluebutton.core.RedisMsgHdlrActor
import org.bigbluebutton.core.json.{ IncomingEventBus2x, ReceivedJsonMessage }

trait UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    log.warning("Unhandled json message: " + msg.data)
  }
}
