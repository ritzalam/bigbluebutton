package org.bigbluebutton.core.api.json.handlers.whiteboard

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{
  SendWbHistoryReplyInMsg2x,
  SendWbHistoryReplyInMsg2xBody
}
import org.bigbluebutton.core.api.json.{ InJsonMsgProtocol, JsonMsgUnmarshaller }
import org.bigbluebutton.core.apps.whiteboard._
import org.bigbluebutton.core.domain._
import spray.json.DefaultJsonProtocol
import spray.json._

class SendWbHistoryReplyJsonMsgHdlrTests extends UnitSpec {

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
    val textAnnotationType = AnnotationType("text")

    val textA = TextAnnotation(text, textBoxHeight, textBoxWidth, fontColor, fontSize, x,
      calcedFontSize, dataPoints, y, status, annotationID, textAnnotationType)

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

    val annotations = Vector[Annotation](textA, shapeA)
    val ah = AnnotationHistory(annotations)

    val messageName = "SendWbHistoryReply"
    val meetingId = "someMeetingId"
    val senderId = "sender"
    val replyTo = "sessionTokenOfSender"

    val header = InMessageHeader(messageName, meetingId, Some(senderId), Some(replyTo))
    val body = SendWbHistoryReplyInMsg2xBody(ah)

    val msg = SendWbHistoryReplyInMsg2x(header, body)

    val shapeJsonMsg = msg.toJson

    val shapeHeadAndBody = JsonMsgUnmarshaller.decode(shapeJsonMsg.toString)

    shapeHeadAndBody match {
      case Some(hb) =>
        val body = SendWbHistoryReplyJsonMsgHdlrHelper.convertTo(hb.body)
        body match {
          case Some(b) =>
            val headA = b.annotations.value.head
            headA match {
              case annotation: TextAnnotation =>
                assert(annotation.textBoxWidth == textA.textBoxWidth)
              case _ =>
                assert(headA.asInstanceOf[ShapeAnnotation].thickness.value == shapeA.thickness.value)
            }
          case None => fail("Failed to parse message body.")
        }

      case None => {
        fail("Failed to parse message")
      }
    }

  }
}
