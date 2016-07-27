package org.bigbluebutton.core2x.json.handlers.presentation

import org.bigbluebutton.core2x.RedisMsgHdlrActor
import org.bigbluebutton.core2x.api.IncomingMsg.ClearPresentationEventInMessage
import org.bigbluebutton.core2x.apps.presentation.domain.PresentationId
import org.bigbluebutton.core2x.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.presentation.ClearPresentationEventMessage

trait ClearPresentationEventJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, presentationId: PresentationId): Unit = {
      log.debug(s"Publishing ${msg.name} [ $presentationId $senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new ClearPresentationEventInMessage(meetingId, senderId, presentationId)))
    }

    if (msg.name == ClearPresentationEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = ClearPresentationEventMessage.fromJson(msg.data)
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
