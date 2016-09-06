package org.bigbluebutton.endpoint.redis

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.endpoint.redis.json.{ PubSubJsonMsgBus, UnhandledJsonMsgHdlr}
import org.bigbluebutton.red5apps.messages.Red5InJsonMsg

object RedisMsgHdlrActor {
  def props(pubSubJsonMsgBus: PubSubJsonMsgBus): Props =
    Props(classOf[RedisMsgHdlrActor], pubSubJsonMsgBus)
}

class RedisMsgHdlrActor(val pubSubJsonMsgBus: PubSubJsonMsgBus)
    extends Actor with ActorLogging
    with UnhandledJsonMsgHdlr {

  def receive = {
    case msg: Red5InJsonMsg => handleReceivedJsonMsg(msg)
    case _ => // do nothing
  }

}
