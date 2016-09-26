package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.{ BigBlueButtonInMessage, IncomingEventBus2x }
import org.bigbluebutton.core.api.IncomingMsg.GoToPageInEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ InHeaderAndJsonBody }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.GoToPageEventMessage

trait GoToPageEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, pageId: String): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ $pageId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new GoToPageInEventInMessage(meetingId, senderId, pageId)))
    }

    if (msg.header.name == GoToPageEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
