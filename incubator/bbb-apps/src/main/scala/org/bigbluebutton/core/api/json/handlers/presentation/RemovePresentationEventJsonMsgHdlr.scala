package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.api.IncomingMsg.RemovePresentationEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.domain.PresentationId
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.RemovePresentationEventMessage

trait RemovePresentationEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, presentationId: PresentationId): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ $presentationId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new RemovePresentationEventInMessage(meetingId, senderId, presentationId)))
    }

    if (msg.header.name == RemovePresentationEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
