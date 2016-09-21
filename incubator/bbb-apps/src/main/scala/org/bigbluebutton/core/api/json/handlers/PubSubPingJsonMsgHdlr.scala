package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.{ BigBlueButtonInMessage, IncomingEventBus2x }
import org.bigbluebutton.core.api.IncomingMsg.PubSubPingMessageInMsg
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ InHeaderAndJsonBody }
import org.bigbluebutton.messages.PubSubPingMessage

trait PubSubPingJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, system: String,
      timestamp: Long): Unit = {
      log.debug("Publishing PubSubPingMessage [ " + system + "]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PubSubPingMessageInMsg(system, timestamp)))
    }

    if (msg.header.name == PubSubPingMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

