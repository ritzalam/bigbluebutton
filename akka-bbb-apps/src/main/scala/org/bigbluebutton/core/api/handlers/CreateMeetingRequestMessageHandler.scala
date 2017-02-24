package org.bigbluebutton.core.api.handlers

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.common.InHeaderAndJsonBody
import org.bigbluebutton.common.message.{ CreateMeetingRequestMessage2x, CreateMeetingRequestMessageConst, CreateMeetingRequestMessageUnmarshaller }
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.bus.{ BigBlueButtonInMessage, IncomingEventBus2x }

import scala.util.{ Failure, Success }

trait CreateMeetingRequestMessageHandler extends UnhandledJsonMsgHdlr with SystemConfiguration {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(inMsg: CreateMeetingRequestMessage2x): Unit = {
      eventBus.publish(BigBlueButtonInMessage(meetingManagerChannel, inMsg))
    }

    if (msg.header.name == CreateMeetingRequestMessageConst.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
      CreateMeetingRequestMessageUnmarshaller.unmarshall(msg) match {
        case Success(inBody) => publish(inBody)
        case Failure(ex) =>
          log.warning("Failed unmarshall message: " + ex.getMessage)
      }
    } else {
      super.handleReceivedJsonMsg(msg)
    }
  }
}

