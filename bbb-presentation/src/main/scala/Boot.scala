
package org.bigbluebutton

import akka.actor.ActorSystem
import org.bigbluebutton.endpoint.redis.AppsRedisSubscriberActor
import org.bigbluebutton.pubsub.receivers.PresentationReceiver


object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bigbluebutton-presentation-conversion-system")

//  val redisPublisher = new RedisPublisher(system)
//  val msgSender = new MessageSender(redisPublisher)

  val redisMsgReceiver = new PresentationReceiver()
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver),
    "redis-subscriber")

}
