package org.bigbluebutton

import akka.event.{ Logging, LoggingAdapter }
import akka.actor.{ ActorSystem, Props }

import scala.concurrent.duration._
import scala.concurrent.{ Await, Future }
import org.bigbluebutton.endpoint.redis.{ AppsRedisSubscriberActor, KeepAliveRedisPublisher, RedisMessageReceiver2x, RedisPublisher }
import org.bigbluebutton.core.api._
import org.bigbluebutton.core._
import org.bigbluebutton.core.pubsub.receivers.RedisMessageReceiver
import org.bigbluebutton.core.pubsub.senders._
import org.bigbluebutton.core.service.recorder.RedisDispatcher
import org.bigbluebutton.core.service.recorder.RecorderApplication
import org.bigbluebutton.core.bus._
import org.bigbluebutton.core.ingw.{ ApiMessageGW, BigBlueButtonInGW }
import io.vertx.core.Vertx

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bigbluebutton-apps-system")
  implicit val executor = system.dispatcher
  val logger = Logging(system, getClass)

  val eventBus = new IncomingEventBus
  val eventBus2x = new IncomingEventBus2x

  val outgoingEventBus = new OutgoingEventBus

  val outgoingEventBus2x = new OutgoingEventBus2x

  val outGW = new OutMessageGateway(outgoingEventBus)
  val outGW2x = new OutMessageGateway2x(outgoingEventBus2x)

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

  val messageSenderActor2x = system.actorOf(MessageSenderActor2x.props(msgSender), "messageSenderActor2x")
  outgoingEventBus2x.subscribe(messageSenderActor2x, outgoingMessageChannel2x)

  val bbbInGW = new BigBlueButtonInGW(system, eventBus, outGW, outGW2x, eventBus2x)

  val bbbActorWrapper = BbbActorWrapper(eventBus, outGW, outGW2x, eventBus2x)

  val apiGW = new ApiMessageGW(system, bbbActorWrapper)

  val redisMsgReceiver = new RedisMessageReceiver(bbbInGW)

  val incomingJsonMessageBus = new IncomingJsonMessageBus
  val redisMessageHandlerActor = system.actorOf(RedisMsgHdlrActor.props(eventBus2x, incomingJsonMessageBus))
  incomingJsonMessageBus.subscribe(redisMessageHandlerActor, incomingJsonMsgChannel)

  val redisMessageReceiver2x = new RedisMessageReceiver2x(bbbInGW)
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver, incomingJsonMessageBus), "redis-subscriber")

  val keepAliveRedisPublisher = new KeepAliveRedisPublisher(system, redisPublisher)

  val vertx = Vertx.vertx()
  vertx.deployVerticle(new WebApiVerticle(apiGW))
}
