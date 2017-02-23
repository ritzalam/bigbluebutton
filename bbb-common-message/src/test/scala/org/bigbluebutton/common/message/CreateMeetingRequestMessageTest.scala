package org.bigbluebutton.common.message

import org.bigbluebutton.common.message._
import org.bigbluebutton.common.{JsonMarshaller, JsonMsgUnmarshaller, TestFixtures, UnitSpec}
import spray.json.{DefaultJsonProtocol, _}
import org.scalatest._
import scala.util.{Failure, Success}

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

  it should "marshall CreateMeetingRequestMessage" in {

    val foo = CreateMeetingRequestMessage2x(createMeetingHeader, createMeetingPayload)

    object Unmarshaller extends CreateMeetingRequestMessageUnmarshaller

    val json = JsonMarshaller.marshall(foo)

    println(json)

    JsonMsgUnmarshaller.decode(json)  match {
      case Success(msg) =>
        Unmarshaller.unmarshall(msg) match {
          case Success(m) => assert(m.header.name == CreateMeetingRequestMessageConst.NAME)
          case Failure(ex) => fail("failed to decode message")
        }
      case Failure(ex) => fail("Failed to convert message. " + ex.getMessage)
    }

  }
}
