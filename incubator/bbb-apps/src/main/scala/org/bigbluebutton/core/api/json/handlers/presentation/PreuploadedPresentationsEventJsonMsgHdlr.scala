package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.api.IncomingMsg.PreuploadedPresentationsEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.PreuploadedPresentation
import org.bigbluebutton.core.apps.presentation.domain.PresentationId
import org.bigbluebutton.core.domain.IntMeetingId
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.PreuploadedPresentationsEventMessage
import org.bigbluebutton.messages.vo.PreuploadedPresentationBody

trait PreuploadedPresentationsEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, presentations: Set[PreuploadedPresentation]): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ ${presentations.size} ]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PreuploadedPresentationsEventInMessage(meetingId, presentations)))
    }

    if (msg.header.name == PreuploadedPresentationsEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

