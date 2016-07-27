package org.bigbluebutton.core2x.handlers.presentation

import org.bigbluebutton.core2x.RedisMsgRxActor
import org.bigbluebutton.core2x.api.IncomingMsg.GoToPageInEventInMessage
import org.bigbluebutton.core2x.json.handlers.UnhandledJsonMsgRx
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.presentation.GoToPageEventMessage

trait GoToPageEventJsonMsgRx extends UnhandledJsonMsgRx {
  this: RedisMsgRxActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, pageId: String): Unit = {
      log.debug(s"Publishing ${msg.name} [ $pageId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new GoToPageInEventInMessage(meetingId, senderId, pageId)))
    }

    if (msg.name == GoToPageEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = GoToPageEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        pageId <- Option(m.body.pageId)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), pageId)
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
