package org.bigbluebutton.endpoint.redis

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.endpoint.redis.json.{IncomingEventBus2x, IncomingJsonMessageBus, UnhandledJsonMsgHdlr}
import org.bigbluebutton.red5apps.messages.Red5InJsonMsg

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
    case msg: Red5InJsonMsg => handleReceivedJsonMsg(msg)
    case _ => // do nothing
  }

}
