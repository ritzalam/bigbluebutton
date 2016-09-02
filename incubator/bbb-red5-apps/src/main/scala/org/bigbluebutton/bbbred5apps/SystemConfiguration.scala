package org.bigbluebutton.bbbred5apps

import com.typesafe.config.ConfigFactory

import scala.util.Try

trait SystemConfiguration {

  val config = ConfigFactory.load()

  lazy val meetingManagerChannel = Try(config.getString("eventBus.meetingManagerChannel")).getOrElse("MeetingManagerChannel")
  lazy val outgoingMessageChannel = Try(config.getString("eventBus.outgoingMessageChannel")).getOrElse("OutgoingMessageChannel")
}
