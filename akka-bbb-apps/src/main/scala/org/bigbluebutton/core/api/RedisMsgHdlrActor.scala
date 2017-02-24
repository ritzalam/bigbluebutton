package org.bigbluebutton.core.api

import org.bigbluebutton.core.bus.{ IncomingEventBus2x, IncomingJsonMessageBus, ReceivedJsonMessage }
import org.bigbluebutton.common.JsonMsgUnmarshaller
import org.bigbluebutton.common.message.CreateMeetingRequestMessageUnmarshaller
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorLogging
import akka.actor.Props
import org.bigbluebutton.core.api.handlers.CreateMeetingRequestMessageHandler

import scala.util.{ Failure, Success }

object RedisMsgHdlrActor {
  def props(eventBus: IncomingEventBus2x, incomingJsonMessageBus: IncomingJsonMessageBus): Props =
    Props(classOf[RedisMsgHdlrActor], eventBus, incomingJsonMessageBus)
}

class RedisMsgHdlrActor(val eventBus: IncomingEventBus2x, val incomingJsonMessageBus: IncomingJsonMessageBus)
    extends Actor with ActorLogging with CreateMeetingRequestMessageHandler {

  def receive = {
    case msg: ReceivedJsonMessage => {
      JsonMsgUnmarshaller.decode(msg.data) match {
        case Success(m) => handleReceivedJsonMsg(m)
        case Failure(ex) => log.warning("Invalid JSON message. {}", msg.data)
      }
    }
    case _ => // do nothing
  }

}
