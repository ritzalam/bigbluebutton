package org.bigbluebutton

import akka.event.Logging
import akka.actor.ActorSystem
import org.bigbluebutton.endpoint.redis.RedisPublisher
import org.bigbluebutton.endpoint.redis.KeepAliveRedisPublisher
import org.bigbluebutton.endpoint.redis.AppsRedisSubscriberActor
import org.bigbluebutton.core.{ BigBlueButtonInGW, MessageSender, OutMessageGateway }
import org.bigbluebutton.core.pubsub.receivers.RedisMessageReceiver
import org.bigbluebutton.core.service.recorder.RedisDispatcher
import org.bigbluebutton.core.service.recorder.RecorderApplication
import org.bigbluebutton.core.bus._
import org.bigbluebutton.core2x.{ BigBlueButtonActor2x, MsgSenderActor2X, RedisMsgHdlrActor }
import org.bigbluebutton.core2x.json.{ IncomingEventBus2x, IncomingJsonMessageBus }

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bigbluebutton-apps-system")
  implicit val executor = system.dispatcher
  val logger = Logging(system, getClass)

  val eventBus2x = new IncomingEventBus2x

  val outgoingEventBus = new OutgoingEventBus

  val outGW = new OutMessageGateway(outgoingEventBus)

  val redisPublisher = new RedisPublisher(system)
  val msgSender = new MessageSender(redisPublisher)

  val redisDispatcher = new RedisDispatcher(redisHost, redisPort, redisPassword, keysExpiresInSec)
  val recorderApp = new RecorderApplication(redisDispatcher)
  recorderApp.start()

  //val recorderActor = system.actorOf(RecorderActor.props(recorderApp), "recorderActor")
  val newMessageSenderActor = system.actorOf(MsgSenderActor2X.props(msgSender), "newMessageSenderActor")

  //outgoingEventBus.subscribe(recorderActor, outgoingMessageChannel)
  outgoingEventBus.subscribe(newMessageSenderActor, outgoingMessageChannel)

  val incomingJsonMessageBus = new IncomingJsonMessageBus
  val redisMessageHandlerActor = system.actorOf(RedisMsgHdlrActor.props(eventBus2x, incomingJsonMessageBus))
  incomingJsonMessageBus.subscribe(redisMessageHandlerActor, "incoming-json-message")

  val bbbActor2x = system.actorOf(BigBlueButtonActor2x.props(system, eventBus2x, outGW), "bigbluebutton-actor2x")
  eventBus2x.subscribe(bbbActor2x, meetingManagerChannel)

  val bbbInGW = new BigBlueButtonInGW(system, outGW, eventBus2x, incomingJsonMessageBus)
  val redisMsgReceiver = new RedisMessageReceiver(bbbInGW)

  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver), "redis-subscriber")

  val keepAliveRedisPublisher = new KeepAliveRedisPublisher(system, redisPublisher)
}
