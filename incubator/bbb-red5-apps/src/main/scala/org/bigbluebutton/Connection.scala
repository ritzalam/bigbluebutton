package org.bigbluebutton

import akka.actor.{Actor, ActorLogging, Props}

object Connection {
  def props(): Props =
    Props(classOf[Connection])
}


class Connection() extends Actor with ActorLogging {
  log.warning("Creating a new Connection warn")

  def receive = {
    //    case msg: MeetingEnded             => handleMeetingHasEnded(msg)

    case msg: Any => log.warning("Unknown message " + msg)
  }

}
