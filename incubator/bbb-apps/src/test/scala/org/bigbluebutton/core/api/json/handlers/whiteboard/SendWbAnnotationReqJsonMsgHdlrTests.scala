package org.bigbluebutton.core.api.json.handlers.presentation.whiteboard

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ SendPublicChatInMsg2x, SendWbAnnotationReqInMsg2x, SendWbAnnotationReqInMsgBody }
import org.bigbluebutton.core.api.json.handlers.CreateMeetingRequestJsonMsgHdlrHelper
import org.bigbluebutton.core.api.json.{ InJsonMsgProtocol, JsonMsgUnmarshaller }
import org.bigbluebutton.core.apps.whiteboard.{ TextAnnotation, WhiteboardProperties2x }
import org.bigbluebutton.core.domain._
import spray.json.DefaultJsonProtocol

// import scala.collection.JavaConversions._

class SendWbAnnotationReqJsonMsgHdlrTests extends UnitSpec {

  object TestProtocol1 extends DefaultJsonProtocol with InJsonMsgProtocol

  it should "convert json message to scala case class" in {
    import TestProtocol1._

    //    val textAnnotationJson = new JsObject(Map[String, JsValue](
    //      "text" -> JsString("Hello"),
    //      "fontColor" -> JsNumber(0),
    //      "thickness" -> JsNumber(1)))
    //    val ta = textAnnotationJson.convertTo[TextAnnotation]

    //    val ta = TextAnnotation("Hello", 0, 1)

    val textA = TextAnnotation("Hello", new Integer(1), new Integer(0))
    val wbProps1 = new WhiteboardProperties2x(WhiteboardId("AAA"), AnnotationType("text"), textA)
    val textbody = SendWbAnnotationReqInMsgBody(wbProps1)

    val messageName = "SendWbAnnotationReq"
    val meetingId = "someMeetingId"
    val senderId = "sender"
    val replyTo = "sessionTokenOfSender"

    val header = InMessageHeader(messageName, Some(meetingId), Some(senderId), Some(replyTo))

    val msg = SendWbAnnotationReqInMsg2x(header, textbody)
    import spray.json._
    val jsonMsg = msg.toJson

    //    implicit val sendWbAnnotationReqInMsg2xFormat = jsonFormat2(SendWbAnnotationReqInMsg2x)
    //  implicit val sendWbAnnotationReqInMsgBodyFormat = jsonFormat1(SendWbAnnotationReqInMsgBody)
    //  implicit val whiteboardProperties2xFormat = jsonFormat3(WhiteboardProperties2x)

    println("____________________________" + jsonMsg)

    //c reate

    /*
    val jsonStr = """{ "some": "JSON source" }"""

    val jsonAst = jsonStr.parseJson

    val wbMsg = jsonAst.convertTo[SendWbAnnotationReqInMsg2x]


    val headAndBody = JsonMsgUnmarshaller.decode(msg.toJson)

    headAndBody match {
      case Some(hb) =>
        val body = SendWhiteboardJsonMsgHdlrHelper.convertTo(hb.body)
        body match {
          case Some(b) => assert(b.props.externalId.value == externalId)
          case None => fail("Failed to parse message body.")
        }

      case None => fail("Failed to parse message")
    }
*/
    /*
    val textAnnotation = textAnnotationJson.convertTo[TextAnnotation]

    val wProps = WhiteboardProperties2x(WhiteboardId("foo"), AnnotationType("bar"), textAnnotation)

    val msgBody = SendWbAnnotationReqInMsgBody(wProps)

    //    val annotTextStatus: String = "textEdited"
    //    val annotTextShapeType: String = "text"
    //    val annotTextWbId: String = "blahblahTextShape007"
    //
    //    //    val annotProp = new WhiteboardProperties2x(WhiteboardId(annotTextWbId), AnnotationType(annotTextShapeType), aaa1)
    //
    //    val mapWrapper = Annotation(textMap)
    //    val body: SendWbAnnotationReqBody = new SendWbAnnotationReqBody(annotTextWbId,
    //      annotTextShapeType, mapWrapper)
    //
    val messageName = "SendWbAnnotationReq"
    val meetingId = "someMeetingId"
    val senderId = "sender"
    val replyTo = "sessionTokenOfSender"

    val header = new InMessageHeader(messageName, Some(meetingId), Some(senderId), Some(replyTo))

    //    val body1 = SendWbAnnotationReqInMsgBody(annotProp)
    //    val msg = SendWbAnnotationReqInMsg2x(header, body1)


    val msg = new SendWbAnnotationReqInMsg2x(header, msgBody)
    // TODO use the common message java object

    println("* 1 \n" + msg.toJson)

    val json: JsObject = JsonParser(msg.toJson).asJsObject

    println("* 2 \n" + json)

    val inMsgFoo = json.convertTo[SendWbAnnotationReqInMsg2x]

    println(inMsgFoo)

    assert(inMsgFoo.header.name == messageName)

    */

    //        val inHeader = InMessageHeader(messageName, Some(meetingId), Some(senderId), Some(replyTo))
    //        println("* 3 \n" + inHeader.toJson)
    //
    //    //    val lala = inMsgFoo.body.props.annotation.convertTo[Map[String, JsValue]]
    //
    //    val whiteboardId = WhiteboardId(annotTextWbId)
    //    val annotationType = AnnotationType(annotTextShapeType)
    //
    //    val inProps = WhiteboardProperties2x(whiteboardId, annotationType, textMap)
    //
    //    val inBody = SendWbAnnotationReqInMsgBody(inProps)
    //
    //    // data.parseJson.convertTo[Map[String, JsValue]]
    //
    //    println("* 4 \n" + inBody.toJson)
    //
    //    val inMsg = SendWbAnnotationReqInMsg2x(inHeader, inBody)
    //
    //    val inJson = inMsg.toJson
    //    println("* 5 \n" + inJson)
  }
}

