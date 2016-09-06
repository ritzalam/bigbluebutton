package org.bigbluebutton.connections

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.bus.Red5AppsMsgBus

object Connection {
  def props(bus: Red5AppsMsgBus): Props =
    Props(classOf[Connection], bus)
}


class Connection(bus: Red5AppsMsgBus) extends Actor with ActorLogging {
  log.warning("Creating a new Connection warn")

  def receive = {
    //    case msg: MeetingEnded             => handleMeetingHasEnded(msg)

    case msg: Any => log.warning("Unknown message " + msg)
  }

}
