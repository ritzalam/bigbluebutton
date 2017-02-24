package org.bigbluebutton.core.api.handlers

import org.bigbluebutton.common.InHeaderAndJsonBody
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.bus.IncomingEventBus2x

/**
 * Created by ritz on 2017-02-24.
 */
trait UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    log.warning("Unhandled json message: " + msg.origMsg)
  }
}
