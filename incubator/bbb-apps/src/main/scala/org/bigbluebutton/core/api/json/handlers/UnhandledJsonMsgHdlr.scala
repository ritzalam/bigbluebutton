package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.api.json.{ InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }

trait UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    log.warning("Unhandled json message: " + msg.origMsg)
  }
}
