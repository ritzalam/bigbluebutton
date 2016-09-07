package org.bigbluebutton.connections

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.bus.{FromClientMsg, Red5AppsMsgBus}

import scala.collection.mutable

object ConnectionsManager {
  def props(bus: Red5AppsMsgBus): Props =
    Props(classOf[ConnectionsManager], bus)
}

class ConnectionsManager(bus: Red5AppsMsgBus) extends Actor with ActorLogging {
  log.warning("Creating a new MeetingManager warn")

  override def preStart(): Unit = {
    bus.subscribe(self, "connection-manager-actor")
    super.preStart()
  }

  override def postStop(): Unit = {
    bus.unsubscribe(self, "connection-manager-actor")
    super.postStop()
  }

  private val connections = new mutable.HashMap[String, Connection]

  def receive = {
    case msg:FromClientMsg => {
      msg.name match {
        case "ValidateAuthToken" => handleValidateAuthToken(msg)
        case "ClientConnected" => handleClientConnected(msg)
        case "ClientDisconnected" => handleClientDisconnected(msg)
      }
    }
    case msg: Any => log.warning("Unknown message " + msg)
  }



  private def handleValidateAuthToken(msg: FromClientMsg): Unit = {
    log.info("Creating connection [" + msg.connectionId + "]")

    connections.get(msg.connectionId) match {
      case None => {
        if (log.isDebugEnabled) {
          log.debug("Creating connection=[" + msg.connectionId + "]")
        }
        val newConnection = new Connection(bus)
        connections += msg.connectionId -> newConnection

      }
      case Some(conn) => {
        if (log.isDebugEnabled) {
          log.debug("Connection already exists. connectionId=[" + msg.connectionId + "]")
        }
      }
    }
  }

  private def handleClientConnected(msg: FromClientMsg): Unit = {
    log.info("Client connected [" + msg.connectionId + "]")

    connections.get(msg.connectionId) match {
      case None => {
        if (log.isDebugEnabled) {
          log.debug("First encounter of connection=[" + msg.connectionId + "]")
        }
        // val newConnection = new Connection(bus)
        // connections += msg.connectionId -> newConnection

      }
      case Some(conn) => {
        if (log.isDebugEnabled) {
          log.debug("Connection already exists. connectionId=[" + msg.connectionId + "]")
        }
      }
    }
  }

  private def handleClientDisconnected(msg: FromClientMsg) {
    log.info("Client disconnected [" + msg.connectionId + "]")

    connections.get(msg.connectionId) foreach { connection =>
      // connection.actorRef forward msg
    }

    connections -= msg.connectionId
  }

}
