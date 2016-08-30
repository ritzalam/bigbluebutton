package org.bigbluebutton.core

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.api.OutGoingMsg.OutMsg
import org.bigbluebutton.core.api.json.{ BigBlueButtonOutMessage, OutgoingEventBus }

object OutMessageGateway {
  def apply(outgoingEventBus: OutgoingEventBus) =
    new OutMessageGateway(outgoingEventBus)
}

class OutMessageGateway(outgoingEventBus: OutgoingEventBus) extends SystemConfiguration {

  def send(msg: OutMsg) {
    outgoingEventBus.publish(BigBlueButtonOutMessage(outgoingMessageChannel, msg))
  }
}