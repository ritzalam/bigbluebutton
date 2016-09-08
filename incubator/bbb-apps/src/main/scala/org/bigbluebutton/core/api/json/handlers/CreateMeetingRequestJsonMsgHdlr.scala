package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.api.IncomingMsg.{ CreateMeetingRequestInMsg2x, CreateMeetingRequestInMsgBody }
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.api.json._
import org.bigbluebutton.messages.CreateMeetingRequestMessage
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsObject }

import scala.util.{ Failure, Success, Try }

trait CreateMeetingRequestJsonMsgHdlr extends UnhandledJsonMsgHdlr
    with SystemConfiguration {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(inMsg: CreateMeetingRequestInMsg2x): Unit = {
      eventBus.publish(BigBlueButtonInMessage(meetingManagerChannel, inMsg))
    }

    if (msg.header.name == CreateMeetingRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
      CreateMeetingRequestJsonMsgHdlrHelper.convertTo(msg.body) match {
        case Some(inBody) =>
          val inMsg = CreateMeetingRequestInMsg2x(msg.header, inBody)
          publish(inMsg)
        case None =>
          log.warning("Failed to parse: {}" + msg.origMsg)
      }
    } else {
      super.handleReceivedJsonMsg(msg)
    }
  }
}

object CreateMeetingRequestJsonMsgHdlrHelper {
  object JsonDecoderProtol extends DefaultJsonProtocol with InJsonMsgProtocol

  private def convertBody(body: JsObject): Try[CreateMeetingRequestInMsgBody] = {
    import JsonDecoderProtol._
    def to(body: JsObject): CreateMeetingRequestInMsgBody = {
      try {
        body.convertTo[CreateMeetingRequestInMsgBody]
      } catch {
        case de: DeserializationException =>
          throw MessageProcessException("DeserializationException JSON message: [" + body.toJson + "]")
        case re: RuntimeException =>
          throw MessageProcessException("Cannot parse JSON message: [" + body.toJson + "]")
      }
    }

    for {
      b <- Try(to(body))
    } yield b
  }

  def convertTo(body: JsObject): Option[CreateMeetingRequestInMsgBody] = {
    convertBody(body) match {
      case Success(b) => Some(b)
      case Failure(ex) => None
    }
  }
}