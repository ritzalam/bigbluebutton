package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.api.IncomingMsg.PresentationConversionCompletedEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.{ Page, Presentation }
import org.bigbluebutton.core.apps.presentation.domain._
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.core.domain.IntMeetingId
import org.bigbluebutton.messages.presentation.PresentationConversionCompletedEventMessage
import org.bigbluebutton.messages.vo.{ PageBody, PresentationBody }

trait PresentationConversionCompletedEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {

  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, messageKey: String, code: String, presentation: Presentation): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ ${presentation.id} $code]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PresentationConversionCompletedEventInMessage(meetingId, messageKey, code,
            presentation)))
    }

    if (msg.header.name == PresentationConversionCompletedEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

