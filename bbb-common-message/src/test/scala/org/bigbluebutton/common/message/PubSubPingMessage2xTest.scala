package org.bigbluebutton.common.message

import org.bigbluebutton.common.{JsonMarshaller, JsonMsgUnmarshaller, UnitSpec}

import scala.util.{Failure, Success}

/**
  * Created by ralam on 2/23/2017.
  */
class PubSubPingMessage2xTest extends UnitSpec {

  val header = Header(PubSubPingMessage2xConst.NAME, PubSubPingMessage2xConst.CHANNEL)
  val body = PubSubPingMessageBody("bbb-web", System.currentTimeMillis())
  val ping = PubSubPingMessage2x(header, body)

  it should "marshall and unmarshall PubSubPingMessage" in {
    import JsonProtocol._

    object Unmarshaller extends PubSubPingMessageUnmarshaller

    JsonMsgUnmarshaller.decode(foo.toJson.toString())  match {
      case Success(msg) =>
        Unmarshaller.unmarshall(msg) match {
          case Success(m) => assert(m.header.name == PubSubPingMessage2xConst.NAME)
          case Failure(ex) => fail("failed to decode message")
        }
      case Failure(ex) => fail("Failed to convert message. " + ex.getMessage)
    }
  }

  it should "marshall PubSubPingMessage" in {

    object Unmarshaller extends PubSubPingMessageMarshaller

    val json = JsonMarshaller.marshall(ping)

    println(json)

    JsonMsgUnmarshaller.decode(json)  match {
      case Success(msg) =>
        Unmarshaller.unmarshall(msg) match {
          case Success(m) => assert(m.header.name == PubSubPingMessage2xConst.NAME)
          case Failure(ex) => fail("failed to decode message")
        }
      case Failure(ex) => fail("Failed to convert message. " + ex.getMessage)
    }

  }

}
