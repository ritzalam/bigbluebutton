package org.bigbluebutton.core2x.bus.handlers.presentation

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.PresentationPageCountErrorEventInMessage
import org.bigbluebutton.core2x.apps.presentation.domain.PresentationId
import org.bigbluebutton.core2x.bus.handlers.UnhandledReceivedJsonMessageHandler
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.IntMeetingId
import org.bigbluebutton.messages.presentation.PresentationPageCountErrorEventMessage

trait PresentationPageCountErrorEventJsonMessageHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, messageKey: String, code: String, presentationId: PresentationId, numberOfPages: Int, pagesCompleted: Int): Unit = {
      log.debug(s"Publishing ${msg.name} [ $presentationId $code]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PresentationPageCountErrorEventInMessage(meetingId, messageKey, code,
            presentationId, numberOfPages, pagesCompleted)))
    }

    if (msg.name == PresentationPageCountErrorEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = PresentationPageCountErrorEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        messageKey <- Option(m.body.messageKey)
        code <- Option(m.body.code)
        presentationId <- Option(m.body.presentationId)
        numberOfPages <- Option(m.body.numberOfPages)
        pagesCompleted <- Option(m.body.pagesCompleted)
      } yield publish(IntMeetingId(meetingId), messageKey, code, PresentationId(presentationId),
        numberOfPages, pagesCompleted)
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}
