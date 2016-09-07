package org.bigbluebutton.connections

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.bus.{FromClientMsg, Red5AppsMsgBus}

object Connection {
  def props(bus: Red5AppsMsgBus, sessionId: String): Props =
    Props(classOf[Connection], bus, sessionId)
}


class Connection(bus: Red5AppsMsgBus, sessionId: String) extends Actor with ActorLogging {
  log.warning(s"Creating a new Connection: $sessionId warn")


  override def preStart(): Unit = {
    bus.subscribe(self, sessionId)
    super.preStart()
  }

  override def postStop(): Unit = {
    bus.unsubscribe(self, sessionId)
    super.postStop()
  }

  def receive = {
    case msg: FromClientMsg => {
      msg.name match {
        case "ValidateAuthTokenRequestMessage" => handleValidateAuthTokenRequest(msg)
      }
    }

    case msg: Any => log.warning(s"ConnectionActor[$sessionId] Unknown message " + msg)
  }


  private def handleValidateAuthTokenRequest(msg: FromClientMsg): Unit = {

    log.info(s"ValidateAuthToken [$msg.json]")

    // send to pubsub with replychannel
  }

}
