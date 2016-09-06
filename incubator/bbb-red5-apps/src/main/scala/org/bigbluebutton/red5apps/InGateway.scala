package org.bigbluebutton.red5apps

import akka.actor.ActorSystem
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

  def handle(msg: Red5InJsonMsg): Unit = {
    println("\n\n "  + msg.json + " \n\n")
  }


}
