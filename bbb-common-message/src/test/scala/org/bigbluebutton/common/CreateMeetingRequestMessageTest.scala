package org.bigbluebutton.common

import org.bigbluebutton.common.message._
import spray.json.DefaultJsonProtocol
import spray.json._
import scala.util.{Failure, Success}
import org.scalatest._

class CreateMeetingRequestMessageTest extends UnitSpec with TestFixtures {
  object JsonProtocol extends DefaultJsonProtocol with CreateMeetingRequestMessageProtocol

  it should "marshall and unmarshall CreateMeetingRequestMessage" in {
    import JsonProtocol._

    val foo = CreateMeetingRequestMessage2x(createMeetingHeader, createMeetingPayload)

    object Unmarshaller extends CreateMeetingRequestMessageUnmarshaller

    JsonMsgUnmarshaller.decode(foo.toJson.toString())  match {
      case Success(msg) =>
        Unmarshaller.unmarshall(msg) match {
          case Success(m) => assert(m.header.name == CreateMeetingRequestMessageConst.NAME)
          case Failure(ex) => fail("failed to decode message")
        }
      case Failure(ex) => fail("Failed to convert message. " + ex.getMessage)
    }

  }
}
