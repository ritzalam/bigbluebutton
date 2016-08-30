package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.api.IncomingMsg.RemovePresentationEventInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.apps.presentation.domain.PresentationId
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.presentation.RemovePresentationEventMessage

trait RemovePresentationEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, presentationId: PresentationId): Unit = {
      log.debug(s"Publishing ${msg.name} [ $presentationId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new RemovePresentationEventInMessage(meetingId, senderId, presentationId)))
    }

    if (msg.name == RemovePresentationEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = RemovePresentationEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        presentationId <- Option(m.body.presentationId)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), PresentationId(presentationId))
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
