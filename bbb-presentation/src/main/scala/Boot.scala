
package org.bigbluebutton

import akka.actor.ActorSystem
import org.bigbluebutton.endpoint.redis.{AppsRedisSubscriberActor, KeepAliveRedisPublisher, RedisPublisher}
import org.bigbluebutton.pubsub.{MessageSender, PresentationReceiver}


object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bbb-presentation-conversion-system")

  val redisMsgReceiver = new PresentationReceiver()
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver),
  "redis-subscriber")

  val redisPublisher = new RedisPublisher(system)
  val msgSender = new MessageSender(redisPublisher)
  val keepAliveRedisPublisher = new KeepAliveRedisPublisher(system, redisPublisher)
}
