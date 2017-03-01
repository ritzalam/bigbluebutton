package org.bigbluebutton.core

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.common.message.BbbMsg
import org.bigbluebutton.core.bus.{ BigBlueButtonOutMessage2x, OutgoingEventBus2x }

object OutMessageGateway2x {
  def apply(outgoingEventBus: OutgoingEventBus2x) =
    new OutMessageGateway2x(outgoingEventBus)
}

class OutMessageGateway2x(outgoingEventBus: OutgoingEventBus2x) extends SystemConfiguration {

  def send(msg: BbbMsg) {
    outgoingEventBus.publish(BigBlueButtonOutMessage2x(outgoingMessageChannel2x, msg))
  }
}