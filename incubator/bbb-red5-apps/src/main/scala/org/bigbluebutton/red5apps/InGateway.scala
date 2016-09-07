package org.bigbluebutton.red5apps

import akka.actor.ActorSystem
import org.bigbluebutton.bus.{FromClientMsg, Red5AppsMsg, Red5AppsMsgBus}
import org.bigbluebutton.connections.ConnectionsManager
import org.bigbluebutton.endpoint.redis.{AppsRedisSubscriberActor, RedisMessageReceiver, RedisPublisher}
import org.bigbluebutton.{IRed5InGW, Red5OutGateway}
import org.bigbluebutton.red5apps.messages.Red5InJsonMsg

class InGateway(val red5OutGW: Red5OutGateway) extends IRed5InGW with SystemConfiguration {

  println(" ****************** Hello!!!!!!!!!!!!!!!!!")

  implicit val system = ActorSystem("red5-bbb-apps-system")

  val redisPublisher = new RedisPublisher(system)
  val redisMsgReceiver = new RedisMessageReceiver()
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver), "red5-apps-redis-subscriber")

  println("*************** meetingManagerChannel " + meetingManagerChannel + " *******************")

  val bus = new Red5AppsMsgBus
  val connectionsManager = system.actorOf(ConnectionsManager.props(system, bus, redisPublisher),
    "red5-apps-connections-manager")

  def handle(msg: Red5InJsonMsg): Unit = {
    println("\n\n InGW:"  + msg.name + " \n\n")

    msg.name match {
      case "ClientConnected" =>
        bus.publish(Red5AppsMsg("connection-manager-actor", new FromClientMsg(msg.name, msg.json, msg.connectionId, msg.sessionToken)))
      case "ClientDisconnected" =>
        bus.publish(Red5AppsMsg("connection-manager-actor", new FromClientMsg(msg.name, msg.json, msg.connectionId, msg.sessionToken)))
      case "ValidateAuthToken" =>
        bus.publish(Red5AppsMsg(msg.sessionToken, new FromClientMsg(msg.name, msg.json, msg.connectionId, msg.sessionToken)))

      // case all other messages =>
      // publish to connection actor
      case _ =>
        bus.publish(Red5AppsMsg(msg.sessionToken, new FromClientMsg(msg.name, msg.json, msg.connectionId, msg.sessionToken)))
    }

  }


}
