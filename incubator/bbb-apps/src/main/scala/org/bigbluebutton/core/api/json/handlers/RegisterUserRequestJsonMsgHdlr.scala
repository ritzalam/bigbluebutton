package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.{ BigBlueButtonInMessage, IncomingEventBus2x }
import org.bigbluebutton.core.api.IncomingMsg.RegisterUserInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.api.json.{ InHeaderAndJsonBody }
import org.bigbluebutton.messages.RegisterUserRequestMessage

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
