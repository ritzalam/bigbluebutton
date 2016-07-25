package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.PubSubPingMessageInMsg
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.PubSubPingMessage

trait PubSubPingMessageJsonHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, system: String,
      timestamp: Long): Unit = {
      log.debug("Publishing PubSubPingMessage [ " + system + "]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PubSubPingMessageInMsg(system, timestamp)))
    }

    if (msg.name == PubSubPingMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = PubSubPingMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        system <- Option(m.body.system)
        timestamp <- Option(m.body.timestamp)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), system, timestamp)
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}

