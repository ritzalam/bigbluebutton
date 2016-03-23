
package org.bigbluebutton

import akka.actor.ActorSystem
import org.bigbluebutton.endpoint.redis.{AppsRedisSubscriberActor, KeepAliveRedisPublisher, RedisPublisher}
import org.bigbluebutton.pubsub.{MessageSender, PresentationReceiver}

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bbb-presentation-conversion-system")


  val redisPublisher = new RedisPublisher(system)
  val msgSender = new MessageSender(redisPublisher)

  val presentationManager = new PresentationManager(system, msgSender)
  val redisMsgReceiver = new PresentationReceiver(presentationManager)
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver),
    "redis-subscriber")

  val keepAliveRedisPublisher = new KeepAliveRedisPublisher(system, redisPublisher)
}
