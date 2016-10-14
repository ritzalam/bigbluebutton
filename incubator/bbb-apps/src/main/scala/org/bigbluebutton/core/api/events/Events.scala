package org.bigbluebutton.core.api.events

import org.bigbluebutton.core.domain.{ ClientId, IntUserId }

trait EventBody
case class EventHeader(val name: String, val seqNum: Long, val prettyTs: String, val ts: Long)
case class BbbEvent(header: EventHeader, body: EventBody)
case class ClientJoined(userId: IntUserId, clientId: ClientId) extends EventBody
case class VoiceMuted(userId: IntUserId, clientId: ClientId, muted: Boolean) extends EventBody

