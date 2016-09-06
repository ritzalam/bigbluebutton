package org.bigbluebutton.endpoint.redis.json

import org.bigbluebutton.endpoint.redis.RedisMsgHdlrActor
import org.bigbluebutton.red5apps.messages.Red5InJsonMsg


trait UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMsg(msg: Red5InJsonMsg): Unit = {
    log.warning("Unhandled json message: " + msg.json)
  }
}
