package org.bigbluebutton.core2x.json.handlers

import org.bigbluebutton.core2x.RedisMsgHdlrActor
import org.bigbluebutton.core2x.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ SessionToken, IntMeetingId, IntUserId }
import org.bigbluebutton.messages.ValidateAuthTokenRequestMessage

trait ValidateAuthTokenRequestMessageJsonHandler extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, authToken: SessionToken): Unit = {
      log.debug("Publishing ValidateAuthTokenRequestInMessage [ " + meetingId.value)
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new ValidateAuthTokenInMessage(
            meetingId,
            senderId,
            authToken)))
    }

    if (msg.name == ValidateAuthTokenRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = ValidateAuthTokenRequestMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        authToken <- Option(m.body.authToken)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), SessionToken(authToken))
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}
