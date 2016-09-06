package org.bigbluebutton.connections

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.bus.Red5AppsMsgBus

import scala.collection.mutable

object ConnectionsManager {
  def props(bus: Red5AppsMsgBus): Props =
    Props(classOf[ConnectionsManager], bus)
}


class ConnectionsManager(bus: Red5AppsMsgBus) extends Actor with ActorLogging {
  log.warning("Creating a new MeetingManager warn")

  private val connections = new mutable.HashMap[String, Connection]

  def receive = {
//    case msg: MeetingEnded             => handleMeetingHasEnded(msg)
//    case msg: MeetingCreated => handleMeetingCreated(msg)

    case msg: Any => log.warning("Unknown message " + msg)
  }


/*
  private def handleDisconnect(msg: MeetingEnded) {
    log.info("Removing connection [" + msg.meetingId + "]")

    connections.get(msg.meetingId) foreach { connection =>
      connection.actorRef forward msg
    }

    connections -= msg.meetingId
  }

  private def handleConnect(msg: MeetingCreated) {
    log.info("Creating connection [" + msg.meetingId + "]")

    connections.get(msg.meetingId) match {
      case None => {
        if (log.isDebugEnabled) {
          log.debug("Creating connection=[" + msg.meetingId + "]")
        }
        val newConnection = new Connection()
        connections += msg.meetingId -> newConnection

      }
      case Some(conn) => {
        if (log.isDebugEnabled) {
          log.debug("Connection already exists. meetingId=[" + msg.meetingId + "]")
        }
      }
    }
  }
  */
}
