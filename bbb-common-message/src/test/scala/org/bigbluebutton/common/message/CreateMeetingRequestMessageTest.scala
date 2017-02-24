package org.bigbluebutton.common.message

import org.bigbluebutton.common.message._
import org.bigbluebutton.common.{JsonMarshaller, JsonMsgUnmarshaller, TestFixtures, UnitSpec}
import spray.json.{DefaultJsonProtocol, _}
import org.scalatest._
import scala.util.{Failure, Success}

class CreateMeetingRequestMessageTest extends UnitSpec with TestFixtures {
  val createMsg = CreateMeetingRequestMessage2x(createMeetingHeader, createMeetingPayload)

  it should "marshall and unmarshall CreateMeetingRequestMessage" in {

    val json = JsonMarshaller.marshall(createMsg)

    JsonMsgUnmarshaller.decode(json)  match {
      case Success(msg) =>
        CreateMeetingRequestMessageUnmarshaller.unmarshall(msg) match {
          case Success(m) => assert(m.header.name == CreateMeetingRequestMessageConst.NAME)
          case Failure(ex) => fail("failed to decode message")
        }
      case Failure(ex) => fail("Failed to convert message. " + ex.getMessage)
    }
  }

}
