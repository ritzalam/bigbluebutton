package org.bigbluebutton.core2x.bus.handlers.presentation

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.SharePresentationEventInMessage
import org.bigbluebutton.core2x.apps.presentation.domain.PresentationId
import org.bigbluebutton.core2x.bus.handlers.UnhandledReceivedJsonMessageHandler
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.presentation.SharePresentationEventMessage

trait SharePresentationEventJsonMessageHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, presentationId: PresentationId, share: Boolean): Unit = {
      log.debug(s"Publishing ${msg.name} [ $presentationId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new SharePresentationEventInMessage(meetingId, senderId, presentationId, share)))
    }

    if (msg.name == SharePresentationEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = SharePresentationEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        presentationId <- Option(m.body.presentationId)
        share <- Option(m.body.share)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), PresentationId(presentationId), share)
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}
