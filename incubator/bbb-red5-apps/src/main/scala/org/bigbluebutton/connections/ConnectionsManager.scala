package org.bigbluebutton.connections

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import org.bigbluebutton.bus.{FromClientMsg, Red5AppsMsgBus}

import scala.collection.mutable

object ConnectionsManager {
  def props(system: ActorSystem, bus: Red5AppsMsgBus): Props =
    Props(classOf[ConnectionsManager], system, bus)
}

class ConnectionsManager(system: ActorSystem, bus: Red5AppsMsgBus) extends Actor with ActorLogging {
  log.warning("Creating a new ConnectionsManager warn")

  val actorName = "connection-manager-actor"

  override def preStart(): Unit = {
    bus.subscribe(self, actorName)
    super.preStart()
  }

  override def postStop(): Unit = {
    bus.unsubscribe(self, actorName)
    super.postStop()
  }

  private val connections = new mutable.HashMap[String, ActorRef]

  def receive = {
    case msg:FromClientMsg => {
      msg.name match {

        case "ClientConnected" => handleClientConnected(msg)
        case "ClientDisconnected" => handleClientDisconnected(msg)
      }
    }
    case msg: Any => log.warning("Unknown message " + msg)
  }


  private def handleClientConnected(msg: FromClientMsg): Unit = {
    log.info("Client connected [" + msg.sessionId + "]")

    connections.get(msg.sessionId) match {
      case None => {
        if (log.isDebugEnabled) {
          log.debug("First encounter of connection=[" + msg.sessionId + "]")
        }

        val newConnection = system.actorOf(Connection.props(bus, msg.sessionId), msg.sessionId)
        connections += msg.sessionId -> newConnection

      }
      case Some(conn) => {
        if (log.isDebugEnabled) {
          log.debug("Connection already exists. sessionId=[" + msg.sessionId + "]")
        }
      }
    }
  }

  private def handleClientDisconnected(msg: FromClientMsg) {
    log.info("Client disconnected [" + msg.sessionId + "]")

    connections.get(msg.sessionId) foreach { connection =>
      connection forward msg
    }

    connections -= msg.sessionId
  }

}
