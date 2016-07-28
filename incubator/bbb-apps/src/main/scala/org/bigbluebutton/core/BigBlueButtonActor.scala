package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, ActorSystem, Props }
import akka.util.Timeout
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.api.OutGoingMsg._
import org.bigbluebutton.core.api.json.IncomingEventBus2x
import org.bigbluebutton.core.meeting.RunningMeeting

import scala.concurrent.duration._

object BigBlueButtonActor extends SystemConfiguration {
  def props(system: ActorSystem,
    eventBus: IncomingEventBus2x,
    outGW: OutMessageGateway): Props =
    Props(classOf[BigBlueButtonActor], system, eventBus, outGW)
}

class BigBlueButtonActor(val system: ActorSystem,
                         eventBus: IncomingEventBus2x, outGW: OutMessageGateway) extends Actor with ActorLogging {

  implicit def executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  private var meetings = new collection.immutable.HashMap[String, RunningMeeting]

  def receive = {
    case msg: CreateMeetingRequestInMsg => handleCreateMeeting(msg)
    case unhandled => log.warning("Unhandled message:\n" + unhandled)
  }

  private def handleCreateMeeting(msg: CreateMeetingRequestInMsg): Unit = {
    meetings.get(msg.meetingId.value) match {
      case None =>
        log.info("Create meeting request. meetingId={}", msg.mProps.id)
        val m = RunningMeeting(msg.mProps, outGW, eventBus)
        meetings += m.mProps.id.value -> m
        outGW.send(new MeetingCreatedEventOutMsg(m.mProps.id, m.mProps))
      case Some(m) =>
        log.info("Meeting already created. meetingId={}", msg.mProps.id)
      // do nothing
    }
  }

}
