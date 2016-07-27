package org.bigbluebutton.core2x.json.handlers.presentation

import org.bigbluebutton.core2x.RedisMsgHdlrActor
import org.bigbluebutton.core2x.api.IncomingMsg.PresentationConversionUpdateEventInMessage
import org.bigbluebutton.core2x.apps.presentation.domain.PresentationId
import org.bigbluebutton.core2x.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.IntMeetingId
import org.bigbluebutton.messages.presentation.PresentationConversionUpdateEventMessage

trait PresentationConversionUpdateEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, messageKey: String, code: String, presentationId: PresentationId): Unit = {
      log.debug(s"Publishing ${msg.name} [ $presentationId $code]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PresentationConversionUpdateEventInMessage(meetingId, messageKey, code,
            presentationId)))
    }

    if (msg.name == PresentationConversionUpdateEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = PresentationConversionUpdateEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        messageKey <- Option(m.body.messageKey)
        code <- Option(m.body.code)
        presentationId <- Option(m.body.presentationId)
      } yield publish(IntMeetingId(meetingId), messageKey, code, PresentationId(presentationId))
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
