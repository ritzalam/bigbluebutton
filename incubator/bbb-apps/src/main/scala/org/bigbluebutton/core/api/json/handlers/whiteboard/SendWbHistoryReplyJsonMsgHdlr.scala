package org.bigbluebutton.core.api.json.handlers.whiteboard

import org.bigbluebutton.core.api.IncomingMsg.{ SendWbHistoryReplyInMsg2x, SendWbHistoryReplyInMsg2xBody }
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain.{ Annotation, WhiteboardProperties2x }
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.whiteboard.SendWbHistoryReply
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.api.json._
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsObject }

import scala.util.{ Failure, Success, Try }

trait SendWbHistoryReplyJsonMsgHdlr extends UnhandledJsonMsgHdlr
    with SystemConfiguration {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(inMsg: SendWbHistoryReplyInMsg2x): Unit = {
      eventBus.publish(BigBlueButtonInMessage(meetingManagerChannel, inMsg))
    }

    if (msg.header.name == SendWbHistoryReply.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
      SendWbHistoryReplyJsonMsgHdlrHelper.convertTo(msg.body) match {
        case Some(inBody) =>
          val inMsg = SendWbHistoryReplyInMsg2x(msg.header, inBody)
          publish(inMsg)
        case None =>
          log.warning("Failed to parse: {}" + msg.origMsg)
      }
    } else {
      super.handleReceivedJsonMsg(msg)
    }
  }
}

object SendWbHistoryReplyJsonMsgHdlrHelper {
  object JsonDecoderProtol extends DefaultJsonProtocol with InJsonMsgProtocol

  private def convertBody(body: JsObject): Try[SendWbHistoryReplyInMsg2xBody] = {
    import JsonDecoderProtol._
    def to(body: JsObject): SendWbHistoryReplyInMsg2xBody = {
      try {
        body.convertTo[SendWbHistoryReplyInMsg2xBody]
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

  def convertTo(body: JsObject): Option[SendWbHistoryReplyInMsg2xBody] = {
    convertBody(body) match {
      case Success(b) => Some(b)
      case Failure(ex) =>
        print("*******************************" + ex)
        None
    }
  }
}

