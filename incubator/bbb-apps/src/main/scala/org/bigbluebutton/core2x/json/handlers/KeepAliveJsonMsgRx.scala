package org.bigbluebutton.core2x.json.handlers

import org.bigbluebutton.core2x.RedisMsgRxActor
import org.bigbluebutton.core2x.api.IncomingMsg.KeepAliveMessageInMsg
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.KeepAliveMessage

trait KeepAliveJsonMsgRx extends UnhandledJsonMsgRx {
  this: RedisMsgRxActor =>

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

