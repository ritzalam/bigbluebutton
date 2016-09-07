package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.json.InJsonMsgProtocol
import org.bigbluebutton.messages.body.MessageHeader

class ValidateAuthTokenRequestJsonMsgHdlrTest extends UnitSpec {
  import spray.json._

  object TestProtocol1 extends DefaultJsonProtocol with InJsonMsgProtocol

  it should "convert json message to scala case class" in {
    import TestProtocol1._

    val messageName = "ValidateAuthTokenRequestMessage"
    val meetingId = null
    val senderId = "sender"
    val replyTo = null
    val header: MessageHeader = new MessageHeader(messageName, meetingId, senderId, replyTo)

  }
}
