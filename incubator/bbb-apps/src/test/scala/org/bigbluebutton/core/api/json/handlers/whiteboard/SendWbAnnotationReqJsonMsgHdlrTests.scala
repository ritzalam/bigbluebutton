package org.bigbluebutton.core.api.json.handlers.whiteboard

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.SendWbAnnotationReqInMsg2x
import org.bigbluebutton.core.api.json.{ InJsonMsgProtocol, JsonMsgUnmarshaller }
import org.bigbluebutton.core.domain._
import spray.json.DefaultJsonProtocol
import spray.json._

class SendWbAnnotationReqJsonMsgHdlrTests extends UnitSpec {

  object TestProtocol1 extends DefaultJsonProtocol with InJsonMsgProtocol

  it should "convert json message to scala case class" in {
    import TestProtocol1._

    // Text annotation
    val text = AnnotationTextContent("KKKKKKKK\r")
    val textBoxHeight = AnnotationTextBoxHeight(2.747678)
    val textBoxWidth = AnnotationTextBoxWidth(11.747968)
    val fontColor = AnnotationTextFontColor(new Integer(1))
    val fontSize = AnnotationTextFontSize(new Integer(18))
    val y = AnnotationY(41.795666)
    val calcedFontSize = AnnotationTextCalcedFontSize(2.7863777)
    val dataPoints = AnnotationDataPoints("84.52381,41.795666")
    val x = AnnotationX(84.52381)
    val status = AnnotationStatus("DRAW_END")
    val annotationID = AnnotationId("bla_shape_132")
    val annotationType = AnnotationType("text")

    val textA = TextAnnotation(text, textBoxHeight, textBoxWidth, fontColor, fontSize, x,
      calcedFontSize, dataPoints, y, status, annotationID, annotationType)
    val wbProps1 = WhiteboardProperties2x(WhiteboardId("AAA"), textA)

    val messageName = "SendWbAnnotationReq"
    val meetingId = "someMeetingId"
    val senderId = "sender"
    val replyTo = "sessionTokenOfSender"

    val header = InMessageHeader(messageName, Some(meetingId), Some(senderId), Some(replyTo))

    val msg = SendWbAnnotationReqInMsg2x(header, wbProps1)

    val jsonMsg = msg.toJson

    println("*1 ------------------ " + jsonMsg)

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

    // Shape annotation
    val shapeColor = AnnotationShapeColor(1)
    val transparency = AnnotationShapeTransparency(false)
    val thickness = AnnotationShapeThickness(1)
    val a: Vector[Double] = List(84.52381, 10.46123, 89.4546, 41.795666).toVector
    val shapeDataPoints = AnnotationShapeDataPoints(a)
    val shapeStatus = AnnotationStatus("DRAW_END")
    val shapeAnnotationID = AnnotationId("bla_shape_132")
    val shapeAnnotationType = AnnotationType("triangle")

    val shapeA = ShapeAnnotation(shapeColor, transparency, shapeStatus, shapeAnnotationID,
      thickness, shapeDataPoints, shapeAnnotationType)
    val wbProps2 = WhiteboardProperties2x(WhiteboardId("BBB"), shapeA)

    val shapeMessageName = "SendWbAnnotationReq"
    val shapeMeetingId = "someMeetingId"
    val shapeSenderId = "sender"
    val shapeReplyTo = "sessionTokenOfSender"

    val shapeHeader = InMessageHeader(shapeMessageName, Some(shapeMeetingId), Some(shapeSenderId), Some(shapeReplyTo))

    val shapeMsg = SendWbAnnotationReqInMsg2x(shapeHeader, wbProps2)

    val shapeJsonMsg = shapeMsg.toJson

    println("*2 -------------------- " + shapeJsonMsg)

    val shapeHeadAndBody = JsonMsgUnmarshaller.decode(shapeJsonMsg.toString)

    shapeHeadAndBody match {
      case Some(hb) =>
        val body = SendWbAnnotationReqJsonMsgHdlrHelper.convertTo(hb.body)
        body match {
          case Some(b) => assert(b.whiteboardId.value == wbProps2.whiteboardId.value)
          case None => fail("Failed to parse message body.")
        }

      case None => {
        fail("Failed to parse message")
      }
    }

  }
}
