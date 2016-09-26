package org.bigbluebutton.core.meetingsmanager

import akka.actor.{ Actor, ActorLogging, ActorSystem, Props }
import akka.util.Timeout
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.{ IncomingEventBus2x, OutMessageGateway }
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.api.OutGoingMsg._
import org.bigbluebutton.core.meeting.RunningMeeting
import org.bigbluebutton.core.meetingsmanager.handlers.CreateMeetingInMsgHdlr

import scala.concurrent.duration._

object BigBlueButtonActor extends SystemConfiguration {
  def props(system: ActorSystem,
    eventBus: IncomingEventBus2x,
    outGW: OutMessageGateway): Props =
    Props(classOf[BigBlueButtonActor], system, eventBus, outGW)
}

class BigBlueButtonActor(val system: ActorSystem,
  val eventBus: IncomingEventBus2x, val outGW: OutMessageGateway)
    extends Actor with ActorLogging
    with CreateMeetingInMsgHdlr {

  implicit def executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  // Give access only to members of [meetingsmanager] package.
  private[meetingsmanager] var meetings = new collection.immutable.HashMap[String, RunningMeeting]

  def receive = {
    case msg: CreateMeetingRequestInMsg => handleCreateMeeting(msg)
    case unhandled => log.warning("Unhandled message:\n" + unhandled)
  }

}
