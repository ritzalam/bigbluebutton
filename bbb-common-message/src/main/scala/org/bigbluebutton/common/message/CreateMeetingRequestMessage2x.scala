package org.bigbluebutton.common.message

import org.bigbluebutton.common.{InHeaderAndJsonBody, MessageProcessException}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsObject}
import org.bigbluebutton.common.messages.MessagingConstants
import scala.util.{Failure, Success, Try}

case class CreateMeetingRequestMessageBody(id: String, externalId: String,
                                           parentId: String, name: String, record: Boolean,
                                           voiceConfId: String, duration: Int,
                                           autoStartRecording: Boolean, allowStartStopRecording: Boolean,
                                           webcamsOnlyForModerator: Boolean, moderatorPass: String,
                                           viewerPass: String, createTime: Long, createDate: String,
                                           isBreakout: Boolean, sequence: Int)


object CreateMeetingRequestMessageConst {
  val NAME = "CreateMeetingRequestMessage"
  val CHANNEL = MessagingConstants.TO_MEETING_CHANNEL
}

case class CreateMeetingRequestMessage2x(header: Header, body: CreateMeetingRequestMessageBody)

trait CreateMeetingRequestMessageProtocol extends HeaderProtocol {
  this: DefaultJsonProtocol =>

  implicit val createMeetingRequestMessagePayloadFormat = jsonFormat16(CreateMeetingRequestMessageBody)
  implicit val createMeetingRequestMessage2xFormat = jsonFormat2(CreateMeetingRequestMessage2x)
}

trait CreateMeetingRequestMessageUnmarshaller {

  object JsonDecoderProtol extends DefaultJsonProtocol with CreateMeetingRequestMessageProtocol

  private def convertBody(body: JsObject): Try[CreateMeetingRequestMessageBody] = {
    import JsonDecoderProtol._

    def to(body: JsObject): CreateMeetingRequestMessageBody = {
      try {
        body.convertTo[CreateMeetingRequestMessageBody]
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

  override def unmarshall(msg: InHeaderAndJsonBody): Try[CreateMeetingRequestMessage2x] = {
    if (msg.header.name == CreateMeetingRequestMessageConst.NAME) {
      convertBody(msg.body) match {
        case Success(body) => Success(CreateMeetingRequestMessage2x(msg.header, body))
        case Failure(ex) => Failure(ex)
      }
    } else {
      throw MessageProcessException("Invalid JSON message")
    }
  }
}

trait CreateMeetingRequestMessageMarshaller {
  import spray.json._

  object JsonDecoderProtol extends DefaultJsonProtocol with CreateMeetingRequestMessageProtocol

  def marshall(msg: CreateMeetingRequestMessage2x): String = {
    import JsonDecoderProtol._
    msg.toJson.toString()
  }
}