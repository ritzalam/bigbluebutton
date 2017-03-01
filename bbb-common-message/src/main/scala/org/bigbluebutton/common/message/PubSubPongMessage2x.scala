package org.bigbluebutton.common.message

import org.bigbluebutton.common.{InHeaderAndJsonBody, MessageProcessException, PubSubChannels}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsObject}

import scala.util.{Failure, Success, Try}

object PubSubPongMessage2xConst {
  val NAME = "PubSubPongMessage"
  val CHANNEL = PubSubChannels.FROM_SYSTEM_CHANNEL
}

case class PubSubPongMessageBody(system: String, timestamp: Long)

case class PubSubPongMessage2x(header: Header, payload: PubSubPongMessageBody) extends BbbMsg

trait PubSubPongMessageProtocol extends HeaderProtocol {
  this: DefaultJsonProtocol =>

  implicit val pubSubPongMessageBodyFormat = jsonFormat2(PubSubPongMessageBody)
  implicit val pubSubPongMessageFormat = jsonFormat2(PubSubPongMessage2x)
}

trait PubSubPongMessageUnmarshaller {

  object JsonDecoderProtol extends DefaultJsonProtocol with PubSubPongMessageProtocol

  private def convertBody(body: JsObject): Try[PubSubPongMessageBody] = {
    import JsonDecoderProtol._

    def to(body: JsObject): PubSubPongMessageBody = {
      try {
        body.convertTo[PubSubPongMessageBody]
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

  def unmarshall(msg: InHeaderAndJsonBody): Try[PubSubPongMessage2x] = {
    if (msg.header.name == PubSubPongMessage2xConst.NAME) {
      convertBody(msg.body) match {
        case Success(body) => Success(PubSubPongMessage2x(msg.header, body))
        case Failure(ex) => Failure(ex)
      }
    } else {
      throw MessageProcessException("Invalid JSON message")
    }
  }
}

trait PubSubPongMessageMarshaller {
  import spray.json._

  object JsonDecoderProtol extends DefaultJsonProtocol with PubSubPongMessageProtocol

  def marshall(msg: PubSubPongMessage2x): String = {
    import JsonDecoderProtol._
    msg.toJson.toString()
  }
}