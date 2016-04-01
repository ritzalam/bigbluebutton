package org.bigbluebutton.endpoint.redis

import akka.actor.ActorSystem
import redis.RedisClient
import org.bigbluebutton.SystemConfiguration

class RedisPublisher(val system: ActorSystem) extends SystemConfiguration {

  val redis = RedisClient(redisHost, redisPort)(system)

  // Set the name of this client to be able to distinguish when doing
  // CLIENT LIST on redis-cli
  redis.clientSetname("BbbPresentationConversionPub")

  def publish(channel: String, data: String) {
    if ("bigbluebutton:from-bbb-presentation-conv:keepalive" != channel) {
      println("PUBLISH TO [" + channel + "]: \n [" + data + "]")
    }
    redis.publish(channel, data)
  }

}