package org.bigbluebutton.core2x.handlers.presentation

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.ResizeAndMovePageEventInMessage
import org.bigbluebutton.core2x.apps.presentation.domain.{ HeightRatio, WidthRatio, XOffset, YOffset }
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core2x.handlers.UnhandledReceivedJsonMessageHandler
import org.bigbluebutton.messages.presentation.ResizeAndMovePageEventMessage

trait ResizeAndMovePageEventMessageHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, xOffset: XOffset, yOffset: YOffset,
      pageId: String, widthRatio: WidthRatio, heightRatio: HeightRatio): Unit = {
      log.debug(s"Publishing ${msg.name} [ $pageId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new ResizeAndMovePageEventInMessage(meetingId, senderId, xOffset, yOffset, pageId,
            widthRatio, heightRatio)))
    }

    if (msg.name == ResizeAndMovePageEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = ResizeAndMovePageEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        xOffset <- Option(m.body.xOffset)
        yOffset <- Option(m.body.yOffset)
        pageId <- Option(m.body.pageId)
        widthRatio <- Option(m.body.widthRatio)
        heightRatio <- Option(m.body.heightRatio)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), XOffset(xOffset), YOffset(yOffset), pageId, WidthRatio(widthRatio), HeightRatio(heightRatio))
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}
