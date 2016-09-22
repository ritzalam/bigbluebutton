package org.bigbluebutton.core.api.json.handlers.whiteboard

import org.bigbluebutton.core.api.IncomingMsg.SendWbAnnotationReqInMsg2x
import org.bigbluebutton.core.api.json.InHeaderAndJsonBody
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.messages.whiteboard.SendWbAnnotationReq
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.{ BigBlueButtonInMessage, IncomingEventBus2x }
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.api.json._
import org.bigbluebutton.core.apps.whiteboard.WhiteboardProperties2x
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsObject }

import scala.util.{ Failure, Success, Try }

trait SendWbAnnotationReqJsonMsgHdlr extends UnhandledJsonMsgHdlr
    with SystemConfiguration {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(inMsg: SendWbAnnotationReqInMsg2x): Unit = {
      eventBus.publish(BigBlueButtonInMessage(meetingManagerChannel, inMsg))
    }

    if (msg.header.name == SendWbAnnotationReq.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
      SendWbAnnotationReqJsonMsgHdlrHelper.convertTo(msg.body) match {
        case Some(inBody) =>
          val inMsg = SendWbAnnotationReqInMsg2x(msg.header, inBody)
          publish(inMsg)
        case None =>
          log.warning("Failed to parse: {}" + msg.origMsg)
      }
    } else {
      super.handleReceivedJsonMsg(msg)
    }
  }
}

object SendWbAnnotationReqJsonMsgHdlrHelper {
  object JsonDecoderProtol extends DefaultJsonProtocol with InJsonMsgProtocol

  private def convertBody(body: JsObject): Try[WhiteboardProperties2x] = {
    import JsonDecoderProtol._
    def to(body: JsObject): WhiteboardProperties2x = {
      try {
        body.convertTo[WhiteboardProperties2x]
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

  def convertTo(body: JsObject): Option[WhiteboardProperties2x] = {
    convertBody(body) match {
      case Success(b) => Some(b)
      case Failure(ex) =>
        // print("*******************************" + ex)
        None
    }
  }
}

