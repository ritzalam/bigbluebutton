package org.bigbluebutton.endpoint.redis

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.endpoint.redis.json.{IncomingEventBus2x, IncomingJsonMessageBus, ReceivedJsonMessage, UnhandledJsonMsgHdlr}

object RedisMsgHdlrActor {
  def props(eventBus: IncomingEventBus2x, incomingJsonMessageBus: IncomingJsonMessageBus): Props =
    Props(classOf[RedisMsgHdlrActor], eventBus, incomingJsonMessageBus)
}

class RedisMsgHdlrActor(
  val eventBus: IncomingEventBus2x,
  val incomingJsonMessageBus: IncomingJsonMessageBus)
    extends Actor with ActorLogging
    with UnhandledJsonMsgHdlr {

  def receive = {
    case msg: ReceivedJsonMessage => handleReceivedJsonMsg(msg)
    case _ => // do nothing
  }

}
