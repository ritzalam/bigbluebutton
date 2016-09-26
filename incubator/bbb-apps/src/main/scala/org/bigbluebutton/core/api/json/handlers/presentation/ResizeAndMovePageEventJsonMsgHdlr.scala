package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.{ BigBlueButtonInMessage, IncomingEventBus2x }
import org.bigbluebutton.core.api.IncomingMsg.ResizeAndMovePageEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.domain.{ HeightRatio, WidthRatio, XOffset, YOffset }
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ InHeaderAndJsonBody }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.ResizeAndMovePageEventMessage

trait ResizeAndMovePageEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, xOffset: XOffset, yOffset: YOffset,
      pageId: String, widthRatio: WidthRatio, heightRatio: HeightRatio): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ $pageId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new ResizeAndMovePageEventInMessage(meetingId, senderId, xOffset, yOffset, pageId,
            widthRatio, heightRatio)))
    }

    if (msg.header.name == ResizeAndMovePageEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
