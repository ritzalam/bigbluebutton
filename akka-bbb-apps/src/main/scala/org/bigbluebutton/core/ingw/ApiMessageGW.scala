package org.bigbluebutton.core.ingw

import akka.actor.ActorSystem
import akka.event.Logging
import akka.util.Timeout
import org.bigbluebutton.core.BbbActorWrapper
import org.bigbluebutton.core.api.IApiMessageGW

import scala.concurrent.duration._
import akka.pattern.{ ask, pipe }

import scala.concurrent.Await
import scala.util.{ Failure, Success }

class ApiMessageGW(val system: ActorSystem, val bbbActorWrapper: BbbActorWrapper) extends IApiMessageGW {
  val log = Logging(system, getClass)

  implicit def executionContext = system.dispatcher

  implicit val timeout = Timeout(5 seconds)

  def apiCreateMeetingMessage(json: String): String = {
    val future = bbbActorWrapper.bbbActor.ask(json)(5 seconds)
    val result = Await.result(future, timeout.duration).asInstanceOf[String]
    result
  }
}
