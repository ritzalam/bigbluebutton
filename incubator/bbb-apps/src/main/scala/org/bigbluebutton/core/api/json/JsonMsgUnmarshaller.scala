package org.bigbluebutton.core.api.json

import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.InMsg

import scala.util.{ Failure, Success, Try }
import spray.json.{ DeserializationException, JsObject, JsonParser }
import org.parboiled.errors.ParsingException

case class InHeaderAndJsonBody(header: InMessageHeader, body: JsObject, origMsg: String)
case class MessageProcessException(message: String) extends Exception(message)

object JsonMsgUnmarshaller {
  import spray.json._

  object JsonDecoderProtol extends DefaultJsonProtocol with InJsonMsgProtocol

  private def header(msg: JsObject): InMessageHeader = {
    import JsonDecoderProtol._

    msg.fields.get("header") match {
      case Some(header) =>
        header.convertTo[InMessageHeader]
      case None =>
        throw MessageProcessException("Cannot get header information: [" + msg + "]")
    }
  }

  private def body(msg: JsObject): JsObject = {
    msg.fields.get("body") match {
      case Some(body) =>
        body.asJsObject
      case None =>
        throw MessageProcessException("Cannot get body information: [" + msg + "]")
    }
  }

  private def toJsObject(msg: String): JsObject = {
    try {
      JsonParser(msg).asJsObject
    } catch {
      case e: ParsingException => {
        throw MessageProcessException("Cannot parse JSON message: [" + msg + "]")
      }
    }
  }

  private def convertTo(jsonMsg: String): Try[InHeaderAndJsonBody] = {
    for {
      jsonObj <- Try(toJsObject(jsonMsg))
      header <- Try(header(jsonObj))
      payload <- Try(body(jsonObj))
      msg = InHeaderAndJsonBody(header, payload, jsonMsg)
    } yield msg
  }

  def decode(json: String): Option[InHeaderAndJsonBody] = {
    convertTo(json) match {
      case Success(validMsg) => Some(validMsg)
      case Failure(ex) => None
    }
  }
}

