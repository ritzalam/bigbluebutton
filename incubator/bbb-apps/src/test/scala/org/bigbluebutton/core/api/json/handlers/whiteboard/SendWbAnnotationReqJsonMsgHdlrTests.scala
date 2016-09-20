package org.bigbluebutton.core.api.json.handlers.whiteboard

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ SendWbAnnotationReqInMsg2x }
import org.bigbluebutton.core.api.json.{ InJsonMsgProtocol, JsonMsgUnmarshaller }
import org.bigbluebutton.core.domain._
import spray.json.DefaultJsonProtocol
import spray.json._

class SendWbAnnotationReqJsonMsgHdlrTests extends UnitSpec {

  object TestProtocol1 extends DefaultJsonProtocol with InJsonMsgProtocol

  it should "convert json message to scala case class" in {
    import TestProtocol1._

    val text = AnnotationTextContent("Hello")
    val fontColor = AnnotationTextFontColor(new Integer(1))
    val thickness = AnnotationTextThickness(new Integer(0))
    val textA = TextAnnotation(text, fontColor, thickness)
    val wbProps1 = WhiteboardProperties2x(WhiteboardId("AAA"), AnnotationType("text"), textA)

    val messageName = "SendWbAnnotationReq"
    val meetingId = "someMeetingId"
    val senderId = "sender"
    val replyTo = "sessionTokenOfSender"

    val header = InMessageHeader(messageName, Some(meetingId), Some(senderId), Some(replyTo))

    val msg = SendWbAnnotationReqInMsg2x(header, wbProps1)

    val jsonMsg = msg.toJson

    println("*1 " + jsonMsg)

    val headAndBody = JsonMsgUnmarshaller.decode(jsonMsg.toString)

    headAndBody match {
      case Some(hb) =>
        val body = SendWbAnnotationReqJsonMsgHdlrHelper.convertTo(hb.body)
        body match {
          case Some(b) => assert(b.whiteboardId.value == wbProps1.whiteboardId.value)
          case None => fail("Failed to parse message body.")
        }

      case None => {
        fail("Failed to parse message")
      }
    }
  }
}
