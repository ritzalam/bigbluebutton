package org.bigbluebutton.endpoint.redis

import akka.actor.Props
import java.net.InetSocketAddress

import redis.actors.RedisSubscriberActor
import redis.api.pubsub.{ PMessage, Message }
import redis.api.servers.ClientSetname
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.bus.IncomingJsonMessageBus
import org.bigbluebutton.core.bus.ReceivedJsonMessage
import org.bigbluebutton.core.bus.IncomingJsonMessage
import org.bigbluebutton.core.pubsub.receivers.RedisMessageReceiver

object AppsRedisSubscriberActor extends SystemConfiguration {

  val channels = Seq("time")
  val patterns = Seq("bigbluebutton:to-bbb-apps:*", "bigbluebutton:from-voice-conf:*")

  def props(msgReceiver: RedisMessageReceiver, msgReceiver2x: IncomingJsonMessageBus): Props =
    Props(classOf[AppsRedisSubscriberActor], msgReceiver, msgReceiver2x,
      redisHost, redisPort,
      channels, patterns).withDispatcher("akka.rediscala-subscriber-worker-dispatcher")
}

class AppsRedisSubscriberActor(msgReceiver: RedisMessageReceiver, msgReceiver2x: IncomingJsonMessageBus,
  redisHost: String,
  redisPort: Int,
  channels: Seq[String] = Nil, patterns: Seq[String] = Nil)
    extends RedisSubscriberActor(
      new InetSocketAddress(redisHost, redisPort),
      channels, patterns) with SystemConfiguration {

  // Set the name of this client to be able to distinguish when doing
  // CLIENT LIST on redis-cli
  write(ClientSetname("BbbAppsAkkaSub").encodedRequest)

  def onMessage(message: Message) {
    log.error(s"SHOULD NOT BE RECEIVING: $message")
  }

  def onPMessage(pmessage: PMessage) {
    //log.debug(s"RECEIVED:\n $pmessage \n")
    val receivedJsonMessage = new ReceivedJsonMessage(pmessage.channel, pmessage.data)
    msgReceiver2x.publish(IncomingJsonMessage(incomingJsonMsgChannel, receivedJsonMessage))

    msgReceiver.handleMessage(pmessage.patternMatched, pmessage.channel, pmessage.data)
  }
}