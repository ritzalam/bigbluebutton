package org.bigbluebutton.core.api.json.handlers.whiteboard

import org.bigbluebutton.core.api.IncomingMsg.SendWhiteboardAnnotationRequest
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain.{ AnnotationVO, IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.vo.AnnotationBody
import org.bigbluebutton.messages.whiteboard.SendWhiteboardAnnotationRequestEventMessage

trait SendWhiteboardAnnotationRequestEventJsonMsgHdlr
    extends UnhandledJsonMsgHdlr {

  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, annotation: AnnotationVO): Unit = {
      log.debug(s"Publishing ${msg.header.name} [$senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          SendWhiteboardAnnotationRequest(meetingId, senderId, annotation)))
    }

    if (msg.header.name == SendWhiteboardAnnotationRequestEventMessage.NAME) {
      log.debug(s"Received JSON message [ ${msg.header.name}]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

