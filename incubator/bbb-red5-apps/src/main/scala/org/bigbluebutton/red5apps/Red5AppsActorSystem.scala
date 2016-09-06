package org.bigbluebutton.red5apps

import org.bigbluebutton.bbbred5apps.SystemConfiguration
import org.bigbluebutton.{IRed5InGW, IRed5InMsg, Red5OutGateway}
import akka.actor.ActorSystem
import org.bigbluebutton.endpoint.redis.{AppsRedisSubscriberActor, RedisMessageReceiver, RedisPublisher}

class Red5AppsActorSystem(val red5OutGW: Red5OutGateway) extends IRed5InGW with SystemConfiguration {

  println(" ****************** Hello!!!!!!!!!!!!!!!!!")

  implicit val system = ActorSystem("red5-bbb-apps-system")

  val redisPublisher = new RedisPublisher(system)
  val redisMsgReceiver = new RedisMessageReceiver()
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver), "red5-apps-redis-subscriber")

  println("*************** meetingManagerChannel " + meetingManagerChannel + " *******************")

  def handle(msg: IRed5InMsg): Unit = {

  }


}
