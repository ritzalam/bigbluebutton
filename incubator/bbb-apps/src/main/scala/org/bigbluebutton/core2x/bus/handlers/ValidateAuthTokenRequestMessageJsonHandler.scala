package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core2x.api.IncomingMessage.ValidateAuthTokenRequestInMessage
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ AuthToken, IntMeetingId, IntUserId }
import org.bigbluebutton.messages.ValidateAuthTokenRequestMessage

trait ValidateAuthTokenRequestMessageJsonHandler extends UnhandledReceivedJsonMessageHandler {
  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    if (msg.name == ValidateAuthTokenRequestMessage.NAME) {
      val m = ValidateAuthTokenRequestMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        authToken <- Option(m.body.authToken)
      } yield eventBus.publish(
        BigBlueButtonInMessage(meetingId,
          new ValidateAuthTokenRequestInMessage(
            IntMeetingId(meetingId),
            IntUserId(senderId), AuthToken(authToken))))
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}
