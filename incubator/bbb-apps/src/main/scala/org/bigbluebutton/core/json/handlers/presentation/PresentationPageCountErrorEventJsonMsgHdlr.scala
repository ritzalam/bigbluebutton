package org.bigbluebutton.core.json.handlers.presentation

import org.bigbluebutton.core.RedisMsgHdlrActor
import org.bigbluebutton.core.api.IncomingMsg.PresentationPageCountErrorEventInMessage
import org.bigbluebutton.core.apps.presentation.domain.PresentationId
import org.bigbluebutton.core.domain.IntMeetingId
import org.bigbluebutton.core.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.PresentationPageCountErrorEventMessage

trait PresentationPageCountErrorEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
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
      super.handleReceivedJsonMsg(msg)
    }

  }
}
