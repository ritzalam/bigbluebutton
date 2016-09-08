package org.bigbluebutton.core.api.json.handlers.chat

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ SendPublicChatInMsg2x, SendPublicChatInMsgBody }
import org.bigbluebutton.core.api.json.InJsonMsgProtocol
import org.bigbluebutton.core.domain._
import org.bigbluebutton.messages.body.MessageHeader
import org.bigbluebutton.messages.chat.SendPublicChatMessage
import org.bigbluebutton.messages.chat.SendPublicChatMessage.SendPublicChatMessageBody
import org.bigbluebutton.messages.vo.ChatPropertiesBody
import spray.json._

class SendPublicChatJsonMsgHdlrTests extends UnitSpec {

  object TestProtocol1 extends DefaultJsonProtocol with InJsonMsgProtocol

  it should "convert json message to scala case class" in {
    import TestProtocol1._

    val fromColor: String = "0"
    val fromTime: String = "1466104668970"
    val chatType: String = "PUBLIC_CHAT"
    val toUserID: String = "someuserid_2"
    val message: String = "This is a sample chat message"
    val fromUsername: String = "User One"
    val fromUserID: String = "someotheruser_4"
    val toUsername: String = "User Two"
    val fromTimezoneOffset: String = "240"

    val chatPropsBody: ChatPropertiesBody = new ChatPropertiesBody(fromColor, fromTime, chatType,
      toUserID, message, fromUsername, fromUserID, toUsername, fromTimezoneOffset)
    val body: SendPublicChatMessageBody = new SendPublicChatMessageBody(chatPropsBody)

    val messageName = "SendPublicChatMessage"
    val meetingId = "someMeetingId"
    val senderId = "sender"
    val replyTo = "sessionTokenOfSender"

    val header: MessageHeader = new MessageHeader(messageName, meetingId, senderId, replyTo)

    val msg: SendPublicChatMessage = new SendPublicChatMessage(header, body)

    println("* 1 \n" + msg.toJson)

    val json: JsObject = JsonParser(msg.toJson).asJsObject

    println("* 2 \n" + json)

    val inMsgFoo = json.convertTo[SendPublicChatInMsg2x]

    println(inMsgFoo)

    assert(inMsgFoo.header.name == messageName)

    val inHeader = InMessageHeader(messageName, Some(meetingId), Some(senderId), Some(replyTo))
    println("* 3 \n" + inHeader.toJson)

    val inProps = ChatProperties2x(Color(fromColor), FromTime(fromTime), ChatType(chatType),
      IntUserId(toUserID), ChatMessageText(message), Username(fromUsername), IntUserId(fromUserID),
      Username(toUsername), TimeZoneOffset(fromTimezoneOffset))

    val inBody = SendPublicChatInMsgBody(inProps)

    println("* 4 \n" + inBody.toJson)

    val inMsg = SendPublicChatInMsg2x(inHeader, inBody)

    val inJson = inMsg.toJson
    println("* 5 \n" + inJson)
  }
}
