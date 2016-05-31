package org.bigbluebutton.core2x.pubsub.senders

import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.api.{ Constants, MessageNames }
import org.bigbluebutton.core.messaging.Util
import org.bigbluebutton.core2x.api.OutGoingMessage.ValidateAuthTokenReply2x

trait ValidateAuthTokenReplySender {
  val service: MessageSender

  def handleValidateAuthTokenReply(msg: ValidateAuthTokenReply2x) {
    println("**** handleValidateAuthTokenReply *****")
    val json = validateAuthTokenReplyToJson(msg)
    //println("************** Publishing [" + json + "] *******************")
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }

  private def validateAuthTokenReplyToJson(msg: ValidateAuthTokenReply2x): String = {
    val payload = new java.util.HashMap[String, Any]()
    payload.put(Constants.REPLY_TO, "todo")
    payload.put(Constants.VALID, msg.valid.toString)
    payload.put(Constants.USER_ID, msg.requesterId.value)
    payload.put(Constants.AUTH_TOKEN, msg.token.value)
    payload.put(Constants.MEETING_ID, msg.meetingId.value)

    val header = Util.buildHeader(MessageNames.VALIDATE_AUTH_TOKEN_REPLY, None)
    Util.buildJson(header, payload)
  }

}
