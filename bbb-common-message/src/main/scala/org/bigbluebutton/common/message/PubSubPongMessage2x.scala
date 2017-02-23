package org.bigbluebutton.common.message

import org.bigbluebutton.common.messages.MessagingConstants

object PubSubPongMessage2x {
  val NAME = "PubSubPongMessage"
  val CHANNEL = MessagingConstants.TO_SYSTEM_CHANNEL
}

case class PubSubPongMessagePayload(system: String, timestamp: Long)

case class PubSubPongMessage2x(header: Header, payload: PubSubPongMessagePayload)
