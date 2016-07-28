package org.bigbluebutton.core.json.handlers

import org.bigbluebutton.core.RedisMsgHdlrActor
import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMessage
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId, SessionToken }
import org.bigbluebutton.core.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.messages.ValidateAuthTokenRequestMessage

trait ValidateAuthTokenRequestJsonMsgHdlr extends UnhandledJsonMsgHdlr {
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
