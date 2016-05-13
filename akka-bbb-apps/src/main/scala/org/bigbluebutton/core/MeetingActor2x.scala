package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.api.{ RegisterUser2xCommand, ValidateAuthToken }
import org.bigbluebutton.core.bus.IncomingEventBus
import org.bigbluebutton.core.domain.MeetingProperties2x
import org.bigbluebutton.core.filters.UsersHandlerFilter
import org.bigbluebutton.core.models.MeetingState

object MeetingActor2x {
  def props(
    props: MeetingProperties2x,
    bus: IncomingEventBus,
    outGW: OutMessageGateway): Props =
    Props(classOf[MeetingActor2x], props, bus, outGW)
}

class MeetingActor2x(
    val props: MeetingProperties2x,
    val bus: IncomingEventBus,
    val outGW: OutMessageGateway) extends Actor with ActorLogging with UsersHandlerFilter {

  val state: MeetingState = new MeetingState(props)

  def receive = {
    case msg: RegisterUser2xCommand => handleRegisterUser2x(msg)
    case msg: ValidateAuthToken => handleValidateAuthToken2x(msg)
  }

}
