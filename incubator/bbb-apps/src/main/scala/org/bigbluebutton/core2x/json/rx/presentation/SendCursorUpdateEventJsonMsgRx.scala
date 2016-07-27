package org.bigbluebutton.core2x.json.rx.presentation

import org.bigbluebutton.core2x.RedisMsgRxActor
import org.bigbluebutton.core2x.api.IncomingMsg.SendCursorUpdateEventInMessage
import org.bigbluebutton.core2x.json.rx.UnhandledJsonMsgRx
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.presentation.SendCursorUpdateEventMessage

trait SendCursorUpdateEventJsonMsgRx extends UnhandledJsonMsgRx {
  this: RedisMsgRxActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, pageId: String, xPercent: Double,
      yPercent: Double): Unit = {
      log.debug(s"Publishing ${msg.name} [ $pageId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new SendCursorUpdateEventInMessage(meetingId, senderId, pageId, xPercent, yPercent)))
    }

    if (msg.name == SendCursorUpdateEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = SendCursorUpdateEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        pageId <- Option(m.body.pageId)
        xPercent <- Option(m.body.xPercent)
        yPercent <- Option(m.body.yPercent)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), pageId, xPercent, yPercent)
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
