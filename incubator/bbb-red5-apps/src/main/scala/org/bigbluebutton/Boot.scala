package org.bigbluebutton

import akka.event.Logging
import akka.actor.ActorSystem

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bbb-red5-apps-system")
  implicit val executor = system.dispatcher
  val logger = Logging(system, getClass)

  println("START bbb-red5-apps-system")

}
