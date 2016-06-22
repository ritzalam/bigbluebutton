package org.bigbluebutton.core2x

import akka.actor.{ Actor, ActorLogging, ActorSystem, Props }
import akka.util.Timeout
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMessage._
import org.bigbluebutton.core2x.api.OutGoingMessage._
import org.bigbluebutton.core2x.bus.IncomingEventBus2x

import scala.concurrent.duration._

object BigBlueButtonActor2x extends SystemConfiguration {
  def props(system: ActorSystem,
    eventBus: IncomingEventBus2x,
    outGW: OutMessageGateway): Props =
    Props(classOf[BigBlueButtonActor2x], system, eventBus, outGW)
}

class BigBlueButtonActor2x(val system: ActorSystem,
    eventBus: IncomingEventBus2x, outGW: OutMessageGateway) extends Actor with ActorLogging {

  implicit def executionContext = system.dispatcher
  implicit val timeout = Timeout(5 seconds)

  private var meetings = new collection.immutable.HashMap[String, RunningMeeting2x]

  def receive = {
    case msg: CreateMeetingRequestInMessage => handleCreateMeeting(msg)

    // case _ => // do nothing
  }

  private def handleCreateMeeting(msg: CreateMeetingRequestInMessage): Unit = {
    meetings.get(msg.meetingId.value) match {
      case None =>
        log.info("Create meeting request. meetingId={}", msg.mProps.id)

        val m = RunningMeeting2x(msg.mProps, outGW, eventBus)

        meetings += m.mProps.id.value -> m

        outGW.send(new MeetingCreated(m.mProps.id, m.mProps))

      case Some(m) =>
        log.info("Meeting already created. meetingID={}", msg.mProps.id)
      // do nothing

    }
  }

}
