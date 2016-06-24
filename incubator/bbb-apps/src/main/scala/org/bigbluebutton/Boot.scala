package org.bigbluebutton

import akka.event.{ Logging }
import akka.actor.{ ActorSystem }
import org.bigbluebutton.endpoint.redis.RedisPublisher
import org.bigbluebutton.endpoint.redis.KeepAliveRedisPublisher
import org.bigbluebutton.endpoint.redis.AppsRedisSubscriberActor
import org.bigbluebutton.core.BigBlueButtonInGW
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.MessageSenderActor
import org.bigbluebutton.core.RecorderActor
import org.bigbluebutton.core.pubsub.receivers.RedisMessageReceiver
import org.bigbluebutton.core.service.recorder.RedisDispatcher
import org.bigbluebutton.core.service.recorder.RecorderApplication
import org.bigbluebutton.core.bus._
import org.bigbluebutton.core2x.{ MessageSenderActor2x, RedisMessageHandlerActor }
import org.bigbluebutton.core2x.bus.{ IncomingEventBus2x, IncomingJsonMessageBus }

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bigbluebutton-apps-system")
  implicit val executor = system.dispatcher
  val logger = Logging(system, getClass)

  val eventBus = new IncomingEventBus
  val eventBus2x = new IncomingEventBus2x

  val outgoingEventBus = new OutgoingEventBus

  val outGW = new OutMessageGateway(outgoingEventBus)

  val redisPublisher = new RedisPublisher(system)
  val msgSender = new MessageSender(redisPublisher)

  val redisDispatcher = new RedisDispatcher(redisHost, redisPort, redisPassword, keysExpiresInSec)
  val recorderApp = new RecorderApplication(redisDispatcher)
  recorderApp.start()

  val messageSenderActor = system.actorOf(MessageSenderActor.props(msgSender), "messageSenderActor")
  val recorderActor = system.actorOf(RecorderActor.props(recorderApp), "recorderActor")
  val newMessageSenderActor = system.actorOf(MessageSenderActor2x.props(msgSender), "newMessageSenderActor")

  outgoingEventBus.subscribe(messageSenderActor, "outgoingMessageChannel")
  outgoingEventBus.subscribe(recorderActor, "outgoingMessageChannel")
  outgoingEventBus.subscribe(newMessageSenderActor, "outgoingMessageChannel")

  val incomingJsonMessageBus = new IncomingJsonMessageBus
  val redisMessageHandlerActor = system.actorOf(RedisMessageHandlerActor.props(eventBus2x, incomingJsonMessageBus))
  incomingJsonMessageBus.subscribe(redisMessageHandlerActor, "incoming-json-message")

  val bbbInGW = new BigBlueButtonInGW(system, eventBus, outGW, eventBus2x, incomingJsonMessageBus, red5DeskShareIP, red5DeskShareApp)
  val redisMsgReceiver = new RedisMessageReceiver(bbbInGW)

  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver), "redis-subscriber")

  val keepAliveRedisPublisher = new KeepAliveRedisPublisher(system, redisPublisher)
}
