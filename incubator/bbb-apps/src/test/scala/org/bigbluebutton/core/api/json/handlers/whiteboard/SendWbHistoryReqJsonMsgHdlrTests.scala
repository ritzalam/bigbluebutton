package org.bigbluebutton.core.api.json.handlers.whiteboard

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ SendWbHistoryReqInMsg2x, SendWbHistoryReqInMsg2xBody }
import org.bigbluebutton.core.api.json.{ InJsonMsgProtocol, JsonMsgUnmarshaller }
import org.bigbluebutton.core.domain._
import spray.json.DefaultJsonProtocol
import spray.json._

class SendWbHistoryReqJsonMsgHdlrTests extends UnitSpec {

  object TestProtocol1 extends DefaultJsonProtocol with InJsonMsgProtocol

  it should "convert json message to scala case class" in {
    import TestProtocol1._

    val wbId = WhiteboardId("AAA")
    val messageName = "SendWbHistoryReq"
    val meetingId = "someMeetingId"
    val senderId = "sender"
    val replyTo = "sessionTokenOfSender"

    val header = InMessageHeader(messageName, meetingId, Some(senderId), Some(replyTo))

    val msg = SendWbHistoryReqInMsg2x(header, SendWbHistoryReqInMsg2xBody(wbId))

    val jsonMsg = msg.toJson

    val headAndBody = JsonMsgUnmarshaller.decode(jsonMsg.toString)

    headAndBody match {
      case Some(hb) =>
        val body = SendWbHistoryReqJsonMsgHdlrHelper.convertTo(hb.body)
        body match {
          case Some(b) => assert(b.whiteboardId.value == wbId.value)
          case None => fail("Failed to parse message body.")
        }

      case None => {
        fail("Failed to parse message")
      }
    }
  }
}
