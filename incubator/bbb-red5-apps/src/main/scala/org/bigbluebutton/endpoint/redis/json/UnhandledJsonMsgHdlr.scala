package org.bigbluebutton.endpoint.redis.json

import org.bigbluebutton.endpoint.redis.RedisMsgHdlrActor


trait UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    log.warning("Unhandled json message: " + msg.data)
  }
}
