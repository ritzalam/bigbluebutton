package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.api.IncomingMsg.RegisterUserInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.messages.RegisterUserRequestMessage
import org.bigbluebutton.messages.vo.UserInfoBody

trait RegisterUserRequestJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {

    def publish(meetingId: IntMeetingId, msg: RegisterUserInMessage): Unit = {
      log.debug("publishing message [RegisterUserRequestInMessage]")
      eventBus.publish(BigBlueButtonInMessage(meetingId.value, msg))
    }

    if (msg.header.name == RegisterUserRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }
  }
}
