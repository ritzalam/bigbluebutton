package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.api.IncomingMsg.PresentationConversionUpdateEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.domain.PresentationId
import org.bigbluebutton.core.domain.IntMeetingId
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.PresentationConversionUpdateEventMessage

trait PresentationConversionUpdateEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, messageKey: String, code: String, presentationId: PresentationId): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ $presentationId $code]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PresentationConversionUpdateEventInMessage(meetingId, messageKey, code,
            presentationId)))
    }

    if (msg.header.name == PresentationConversionUpdateEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
