package org.bigbluebutton.core2x

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core2x.api.OutGoingMsg.OutMsg
import org.bigbluebutton.core2x.json.{ BigBlueButtonOutMessage, OutgoingEventBus }

object OutMessageGateway {
  def apply(outgoingEventBus: OutgoingEventBus) =
    new OutMessageGateway(outgoingEventBus)
}

class OutMessageGateway(outgoingEventBus: OutgoingEventBus) extends SystemConfiguration {

  def send(msg: OutMsg) {
    outgoingEventBus.publish(BigBlueButtonOutMessage(outgoingMessageChannel, msg))
  }
}