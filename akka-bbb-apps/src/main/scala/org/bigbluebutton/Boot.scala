package org.bigbluebutton

import akka.event.{ Logging, LoggingAdapter }
import akka.actor.{ ActorSystem, Props }

import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }
import org.bigbluebutton.endpoint.redis.{ AppsRedisSubscriberActor, KeepAliveRedisPublisher, RedisMessageReceiver2x, RedisPublisher }
import org.bigbluebutton.core.api.{ IBigBlueButtonInGW, MessageOutGateway, OutMessageListener2, RedisMsgHdlrActor }
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.MessageSenderActor
import org.bigbluebutton.core.RecorderActor
import org.bigbluebutton.core.pubsub.receivers.RedisMessageReceiver
import org.bigbluebutton.core.pubsub.senders._
import org.bigbluebutton.core.service.recorder.RedisDispatcher
import org.bigbluebutton.core.service.recorder.RecorderApplication
import org.bigbluebutton.core.bus._
import org.bigbluebutton.core.JsonMessageSenderActor
import org.bigbluebutton.core.ingw.BigBlueButtonInGW

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bigbluebutton-apps-system")
  implicit val executor = system.dispatcher
  val logger = Logging(system, getClass)

  val eventBus = new IncomingEventBus
  val outgoingEventBus = new OutgoingEventBus

  val outGW = new OutMessageGateway(outgoingEventBus)

  val redisPublisher = new RedisPublisher(system)
  val msgSender = new MessageSender(redisPublisher)

  val redisDispatcher = new RedisDispatcher(redisHost, redisPort, redisPassword, keysExpiresInSec)
  val recorderApp = new RecorderApplication(redisDispatcher)
  recorderApp.start()

  val messageSenderActor = system.actorOf(MessageSenderActor.props(msgSender), "messageSenderActor")
  val recorderActor = system.actorOf(RecorderActor.props(recorderApp), "recorderActor")
  val newMessageSenderActor = system.actorOf(JsonMessageSenderActor.props(msgSender), "newMessageSenderActor")

  outgoingEventBus.subscribe(messageSenderActor, "outgoingMessageChannel")
  outgoingEventBus.subscribe(recorderActor, "outgoingMessageChannel")
  outgoingEventBus.subscribe(newMessageSenderActor, "outgoingMessageChannel")

  val bbbInGW = new BigBlueButtonInGW(system, eventBus, outGW, red5DeskShareIP, red5DeskShareApp)
  val redisMsgReceiver = new RedisMessageReceiver(bbbInGW)

  val eventBus2x = new IncomingEventBus2x
  val incomingJsonMessageBus = new IncomingJsonMessageBus
  val redisMessageHandlerActor = system.actorOf(RedisMsgHdlrActor.props(eventBus2x, incomingJsonMessageBus))
  incomingJsonMessageBus.subscribe(redisMessageHandlerActor, "incoming-json-message")

  val redisMessageReceiver2x = new RedisMessageReceiver2x(bbbInGW)
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver, incomingJsonMessageBus), "redis-subscriber")

  val keepAliveRedisPublisher = new KeepAliveRedisPublisher(system, redisPublisher)
}
