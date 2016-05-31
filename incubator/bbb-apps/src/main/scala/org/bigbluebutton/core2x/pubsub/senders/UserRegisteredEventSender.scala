package org.bigbluebutton.core2x.pubsub.senders

import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.api.{ Constants, MessageNames }
import org.bigbluebutton.core.messaging.Util
import org.bigbluebutton.core2x.api.OutGoingMessage.UserRegisteredEvent2x
import org.bigbluebutton.core2x.domain.RegisteredUser2x

import scala.collection.JavaConversions._

trait UserRegisteredEventSender {
  val service: MessageSender

  def handleUserRegistered(msg: UserRegisteredEvent2x) {
    val json = userRegisteredToJson(msg)
    service.send(MessagingConstants.FROM_MEETING_CHANNEL, json)
    handleRegisteredUser(msg);
  }

  private def handleRegisteredUser(msg: UserRegisteredEvent2x) {
    val json = userRegisteredToJson(msg)
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }

  private def userRegisteredToJson(msg: UserRegisteredEvent2x): String = {
    val payload = new java.util.HashMap[String, Any]()
    payload.put(Constants.MEETING_ID, msg.meetingId.value)
    payload.put(Constants.USER, registeredUserToMap(msg.user))
    payload.put(Constants.RECORDED, msg.recorded.value)

    val header = Util.buildHeader(MessageNames.USER_REGISTERED, None)
    Util.buildJson(header, payload)
  }

  private def registeredUserToMap(user: RegisteredUser2x): java.util.Map[String, Any] = {
    val wuser = new scala.collection.mutable.HashMap[String, Any]
    wuser += "userid" -> user.id.value
    wuser += "extern_userid" -> user.extId.value
    wuser += "name" -> user.name.value
    wuser += "role" -> user.roles.toArray
    wuser += "authToken" -> user.authToken.value

    mapAsJavaMap(wuser)
  }
}
