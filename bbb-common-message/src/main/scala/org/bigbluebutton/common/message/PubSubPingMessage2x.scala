package org.bigbluebutton.common.message

import org.bigbluebutton.common.messages.MessagingConstants

object PubSubPingMessage2x {
  val NAME = "PubSubPingMessage"
  val CHANNEL = MessagingConstants.TO_SYSTEM_CHANNEL
}

case class PubSubPingMessagePayload(system: String, timestamp: Long)

case class PubSubPingMessage2x(header: Header, payload: PubSubPingMessagePayload)


