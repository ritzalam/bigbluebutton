package org.bigbluebutton.core2x.json.rx

import org.bigbluebutton.core2x.RedisMsgRxActor
import org.bigbluebutton.core2x.api.IncomingMsg.PubSubPingMessageInMsg
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }
import org.bigbluebutton.messages.PubSubPingMessage

trait PubSubPingJsonMsgRx extends UnhandledJsonMsgRx {
  this: RedisMsgRxActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
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
      super.handleReceivedJsonMsg(msg)
    }

  }
}

