package org.bigbluebutton.common

import scala.util.{Failure, Success, Try}
import org.bigbluebutton.common.message.{Header, HeaderProtocol}
import spray.json.JsObject
import spray.json.JsonParser.ParsingException

case class InHeaderAndJsonBody(header: Header, body: JsObject, origMsg: String)
case class MessageProcessException(message: String) extends Exception(message)

trait

object JsonMsgUnmarshaller {
  import spray.json._

  object JsonDecoderProtol extends DefaultJsonProtocol with HeaderProtocol

  private def header(msg: JsObject): Header = {
    import JsonDecoderProtol._
    msg.fields.get("header") match {
      case Some(header) =>
        header.convertTo[Header]
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

  def decode(json: String): Try[InHeaderAndJsonBody] = {
    convertTo(json)
  }

}
