package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.api.IncomingMsg.KeepAliveMessageInMsg
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.messages.KeepAliveMessage

trait KeepAliveJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, aliveID: String): Unit = {
      log.debug("Publishing KeepAliveMessage [ " + aliveID + "]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new KeepAliveMessageInMsg(aliveID)))
    }

    if (msg.name == KeepAliveMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = KeepAliveMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        aliveID <- Option(m.body.aliveID)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), aliveID)
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

