package org.bigbluebutton.core2x.json.rx

import org.bigbluebutton.core2x.RedisMsgRxActor
import org.bigbluebutton.core2x.json.{ IncomingEventBus2x, ReceivedJsonMessage }

trait UnhandledJsonMsgRx {
  this: RedisMsgRxActor =>

  val eventBus: IncomingEventBus2x

  def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    log.warning("Unhandled json message: " + msg.data)
  }
}
