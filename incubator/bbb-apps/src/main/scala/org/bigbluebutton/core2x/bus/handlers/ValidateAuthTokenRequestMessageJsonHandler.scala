package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMessage.ValidateAuthTokenRequestInMessage
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ AuthToken, IntMeetingId, IntUserId }
import org.bigbluebutton.messages.ValidateAuthTokenRequestMessage

trait ValidateAuthTokenRequestMessageJsonHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, authToken: AuthToken): Unit = {
      log.debug("Publishing ValidateAuthTokenRequestInMessage [ " + meetingId.value)
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new ValidateAuthTokenRequestInMessage(
            meetingId,
            senderId,
            authToken)))
    }

    if (msg.name == ValidateAuthTokenRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = ValidateAuthTokenRequestMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        authToken <- Option(m.body.authToken)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), AuthToken(authToken))
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}
