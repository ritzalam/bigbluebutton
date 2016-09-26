package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.{ BigBlueButtonInMessage, IncomingEventBus2x }
import org.bigbluebutton.core.api.IncomingMsg.ClearPresentationEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.domain.PresentationId
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ InHeaderAndJsonBody }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.ClearPresentationEventMessage

trait ClearPresentationEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, presentationId: PresentationId): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ $presentationId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new ClearPresentationEventInMessage(meetingId, senderId, presentationId)))
    }

    if (msg.header.name == ClearPresentationEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
