package org.bigbluebutton.core.api

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.IncomingEventBus2x
import org.bigbluebutton.core.api.json.handlers._
import org.bigbluebutton.core.api.json.{ IncomingJsonMessageBus, JsonMsgUnmarshaller, ReceivedJsonMessage }

object RedisMsgHdlrActor {
  def props(eventBus: IncomingEventBus2x, incomingJsonMessageBus: IncomingJsonMessageBus): Props =
    Props(classOf[RedisMsgHdlrActor], eventBus, incomingJsonMessageBus)
}

class RedisMsgHdlrActor(
  val eventBus: IncomingEventBus2x,
  val incomingJsonMessageBus: IncomingJsonMessageBus)
    extends Actor with ActorLogging
    with UnhandledJsonMsgHdlr
    with CreateMeetingRequestJsonMsgHdlr {

  def receive = {
    case msg: ReceivedJsonMessage => {
      JsonMsgUnmarshaller.decode(msg.data) match {
        case Some(m) => handleReceivedJsonMsg(m)
        case None => log.warning("Invalid JSON message. {}", msg.data)
      }
    }
    case _ => // do nothing
  }

}
