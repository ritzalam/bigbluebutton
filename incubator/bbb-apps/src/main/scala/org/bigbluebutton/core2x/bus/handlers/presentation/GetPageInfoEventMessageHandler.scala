package org.bigbluebutton.core2x.bus.handlers.presentation

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.GetPageInfoEventInMessage
import org.bigbluebutton.core2x.bus.handlers.UnhandledReceivedJsonMessageHandler
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.presentation.GetPageInfoEventMessage

trait GetPageInfoEventMessageHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, pageId: String): Unit = {
      log.debug(s"Publishing ${msg.name} [ $pageId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new GetPageInfoEventInMessage(meetingId, senderId, pageId)))
    }

    if (msg.name == GetPageInfoEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = GetPageInfoEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        pageId <- Option(m.body.pageId)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), pageId)
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}
