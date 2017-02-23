package org.bigbluebutton.core.api

import org.bigbluebutton.core.bus.{ IncomingJsonMessageBus, ReceivedJsonMessage }
import org.bigbluebutton.common.JsonMsgUnmarshaller
import org.bigbluebutton.common.message.CreateMeetingRequestMessageUnmarshaller

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorLogging
import akka.actor.Props
import scala.util.{ Failure, Success }

object RedisMsgHdlrActor {
  def props(incomingJsonMessageBus: IncomingJsonMessageBus): Props =
    Props(classOf[RedisMsgHdlrActor], incomingJsonMessageBus)
}

class RedisMsgHdlrActor(val incomingJsonMessageBus: IncomingJsonMessageBus)
    extends Actor with ActorLogging with CreateMeetingRequestMessageUnmarshaller {

  def receive = {
    case msg: ReceivedJsonMessage => {
      JsonMsgUnmarshaller.decode(msg.data) match {
        case Success(m) => unmarshall(m)
        case Failure(ex) => log.warning("Invalid JSON message. {}", msg.data)
      }
    }
    case _ => // do nothing
  }

}
