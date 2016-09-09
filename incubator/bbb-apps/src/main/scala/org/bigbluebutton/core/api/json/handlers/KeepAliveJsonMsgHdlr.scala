package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.api.IncomingMsg.KeepAliveMessageInMsg
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.messages.KeepAliveMessage

trait KeepAliveJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, aliveID: String): Unit = {
      log.debug("Publishing KeepAliveMessage [ " + aliveID + "]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new KeepAliveMessageInMsg(aliveID)))
    }

    if (msg.header.name == KeepAliveMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

