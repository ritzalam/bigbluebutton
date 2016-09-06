package org.bigbluebutton.bbbred5apps

import com.typesafe.config.ConfigFactory

import scala.util.Try

trait SystemConfiguration {

  val config = ConfigFactory.load()

  lazy val redisHost = Try(config.getString("redis.host")).getOrElse("127.0.0.1")
  lazy val redisPort = Try(config.getInt("redis.port")).getOrElse(6379)
  lazy val redisPassword = Try(config.getString("redis.password")).getOrElse("")

  lazy val meetingManagerChannel = Try(config.getString("eventBus.meetingManagerChannel")).getOrElse("MeetingManagerChannel")
  lazy val outgoingMessageChannel = Try(config.getString("eventBus.outgoingMessageChannel")).getOrElse("OutgoingMessageChannel")
}
