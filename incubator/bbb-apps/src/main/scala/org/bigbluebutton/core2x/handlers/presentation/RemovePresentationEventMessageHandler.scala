package org.bigbluebutton.core2x.handlers.presentation

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.RemovePresentationEventInMessage
import org.bigbluebutton.core2x.apps.presentation.domain.PresentationId
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core2x.handlers.UnhandledReceivedJsonMessageHandler
import org.bigbluebutton.messages.presentation.RemovePresentationEventMessage

trait RemovePresentationEventMessageHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, presentationId: PresentationId): Unit = {
      log.debug(s"Publishing ${msg.name} [ $presentationId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new RemovePresentationEventInMessage(meetingId, senderId, presentationId)))
    }

    if (msg.name == RemovePresentationEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = RemovePresentationEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        presentationId <- Option(m.body.presentationId)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), PresentationId(presentationId))
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}
