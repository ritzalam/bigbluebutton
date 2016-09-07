package org.bigbluebutton.connections

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import org.bigbluebutton.bus.{FromClientMsg, Red5AppsMsgBus}
import org.bigbluebutton.endpoint.redis.RedisPublisher

import scala.collection.mutable

object ConnectionsManager {
  def props(system: ActorSystem, bus: Red5AppsMsgBus, redisPublisher: RedisPublisher): Props =
    Props(classOf[ConnectionsManager], system, bus, redisPublisher)
}

class ConnectionsManager(system: ActorSystem, bus: Red5AppsMsgBus, redisPublisher:
RedisPublisher) extends Actor with ActorLogging {
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
        case "ClientConnected"      => handleClientConnected(msg)
        case "ClientDisconnected"   => handleClientDisconnected(msg)

        case _                      => handleTransitMessage(msg)
      }
    }
    case msg: Any => log.warning("Unknown message " + msg)
  }


  private def handleClientConnected(msg: FromClientMsg): Unit = {
    log.info(s"Client connected sToken=${msg.sessionToken} connId=${msg.connectionId}")

    connections.get(msg.sessionToken) match {
      case None => {
        if (log.isDebugEnabled) {
          log.debug(s"First encounter of connId=${msg.connectionId} for sToken=${msg.sessionToken}")
        }

        val newConnection = system.actorOf(Connection.props(bus, redisPublisher, msg
          .sessionToken, msg.connectionId), msg.sessionToken)
        connections += msg.sessionToken -> newConnection

        newConnection ! msg

      }
      case Some(conn) => {
        if (log.isDebugEnabled) {
          log.debug(s"Connection connId=${msg.connectionId} for sToken=${msg.sessionToken} already exists")
        }
        conn ! msg
      }
    }
  }

  private def handleClientDisconnected(msg: FromClientMsg) {
    log.info(s"Client disconnected sToken=${msg.sessionToken} connId=${msg.connectionId}")

    connections.get(msg.sessionToken) foreach { connection =>
      connection forward msg
    }

    connections -= msg.sessionToken
  }

  private def handleTransitMessage(msg: FromClientMsg): Unit = {
    connections.get(msg.sessionToken) foreach { connection =>
      connection ! msg
    }
  }

}
