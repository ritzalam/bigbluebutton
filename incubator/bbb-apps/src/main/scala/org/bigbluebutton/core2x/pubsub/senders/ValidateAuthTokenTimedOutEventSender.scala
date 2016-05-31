package org.bigbluebutton.core2x.pubsub.senders

import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.api.{ Constants, MessageNames }
import org.bigbluebutton.core.messaging.Util
import org.bigbluebutton.core2x.api.OutGoingMessage.ValidateAuthTokenTimedOut

trait ValidateAuthTokenTimedOutEventSender {
  val service: MessageSender

  def handleValidateAuthTokenTimedOut(msg: ValidateAuthTokenTimedOut) {
    println("**** handleValidateAuthTokenTimedOut *****")
    val json = validateAuthTokenTimeoutToJson(msg)
    //println("************** Publishing [" + json + "] *******************")
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }

  private def validateAuthTokenTimeoutToJson(msg: ValidateAuthTokenTimedOut): String = {
    val payload = new java.util.HashMap[String, Any]()
    payload.put(Constants.REPLY_TO, msg.correlationId)
    payload.put(Constants.VALID, msg.valid.toString)
    payload.put(Constants.AUTH_TOKEN, msg.token)
    payload.put(Constants.USER_ID, msg.requesterId)
    payload.put(Constants.MEETING_ID, msg.meetingId.value)

    val header = Util.buildHeader(MessageNames.VALIDATE_AUTH_TOKEN_TIMEOUT, None)
    Util.buildJson(header, payload)
  }
}
