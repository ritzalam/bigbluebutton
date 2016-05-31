package org.bigbluebutton.core

import akka.actor.ActorContext
import org.bigbluebutton.core.bus._
import org.bigbluebutton.core.domain.MeetingProperties

object RunningMeeting {
  def apply(mProps: MeetingProperties, outGW: OutMessageGateway,
    eventBus: IncomingEventBus)(implicit context: ActorContext) =
    new RunningMeeting(mProps, outGW, eventBus)(context)
}

class RunningMeeting(val mProps: MeetingProperties, val outGW: OutMessageGateway,
    val eventBus: IncomingEventBus)(implicit val context: ActorContext) {

  val actorRef = context.actorOf(MeetingActor.props(mProps, eventBus, outGW), mProps.id.value)

}