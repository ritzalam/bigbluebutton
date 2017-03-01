package org.bigbluebutton.common.message

import org.bigbluebutton.common.{InHeaderAndJsonBody, MessageProcessException, PubSubChannels}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsObject}

import scala.util.{Failure, Success, Try}

object PubSubPingMessage2xConst {
  val NAME = "PubSubPingMessage"
  val CHANNEL = PubSubChannels.TO_SYSTEM_CHANNEL
}

case class PubSubPingMessageBody(system: String, timestamp: Long)

case class PubSubPingMessage2x(header: Header, body: PubSubPingMessageBody) extends BbbMsg

trait PubSubPingMessageProtocol extends HeaderProtocol {
  this: DefaultJsonProtocol =>

  implicit val pubSubPingMessageBodyFormat = jsonFormat2(PubSubPingMessageBody)
  implicit val pubSubPingMessageFormat = jsonFormat2(PubSubPingMessage2x)
}

object PubSubPingMessageUnmarshaller {

  object JsonDecoderProtol extends DefaultJsonProtocol with PubSubPingMessageProtocol

  private def convertBody(body: JsObject): Try[PubSubPingMessageBody] = {
    import JsonDecoderProtol._

    def to(body: JsObject): PubSubPingMessageBody = {
      try {
        body.convertTo[PubSubPingMessageBody]
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

  def unmarshall(msg: InHeaderAndJsonBody): Try[PubSubPingMessage2x] = {
    if (msg.header.name == PubSubPingMessage2xConst.NAME) {
      convertBody(msg.body) match {
        case Success(body) => Success(PubSubPingMessage2x(msg.header, body))
        case Failure(ex) => Failure(ex)
      }
    } else {
      throw MessageProcessException("Invalid JSON message")
    }
  }
}

trait PubSubPingMessageMarshaller {
  def marshall(msg: PubSubPingMessage2x): String = {
    import spray.json._

    object JsonDecoderProtol extends DefaultJsonProtocol with PubSubPingMessageProtocol
    import JsonDecoderProtol._
    msg.toJson.toString()
  }
}
