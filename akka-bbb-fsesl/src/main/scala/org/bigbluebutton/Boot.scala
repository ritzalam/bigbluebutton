package org.bigbluebutton

import akka.actor.{ActorSystem, Props}
import freeswitch.pubsub.receivers.RedisMessageReceiver

import scala.concurrent.duration._
import redis.RedisClient

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import org.freeswitch.esl.client.manager.DefaultManagerConnection
import org.bigbluebutton.endpoint.redis.{AppsRedisSubscriberActor, RedisPublisher}
import org.bigbluebutton.freeswitch.VoiceConferenceService
import freeswitch.voice.{FreeswitchConferenceEventListener, IVoiceConferenceService}
import freeswitch.voice.freeswitch.{ConnectionManager, ESLEventListener, FreeswitchApplication}

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bigbluebutton-fsesl-system")

  val redisPublisher = new RedisPublisher(system)

  val eslConnection = new DefaultManagerConnection(eslHost, eslPort, eslPassword);

  val voiceConfService = new VoiceConferenceService(redisPublisher)

  val fsConfEventListener = new FreeswitchConferenceEventListener(voiceConfService)
  fsConfEventListener.start()

  val eslEventListener = new ESLEventListener(fsConfEventListener)
  val connManager = new ConnectionManager(eslConnection, eslEventListener, fsConfEventListener)

  connManager.start()

  val fsApplication = new FreeswitchApplication(connManager, fsProfile)
  fsApplication.start()

  val redisMsgReceiver = new RedisMessageReceiver(fsApplication)

  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(system, redisMsgReceiver), "redis-subscriber")
}
