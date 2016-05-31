package org.bigbluebutton.core2x.pubsub.senders

import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.api.{ Constants, MessageNames }
import org.bigbluebutton.core.messaging.Util
import org.bigbluebutton.core2x.api.OutGoingMessage.UserJoinedEvent2x
import org.bigbluebutton.core2x.domain.User3x
import scala.collection.JavaConversions._

trait UserJoinedEventSender {

  val service: MessageSender

  def handleUserJoinedEvent2x(msg: UserJoinedEvent2x): Unit = {
    val json = userJoinedToJson(msg)
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }

  private def userJoinedToJson(msg: UserJoinedEvent2x): String = {
    val payload = new java.util.HashMap[String, Any]()
    payload.put(Constants.MEETING_ID, msg.meetingId.value)
    payload.put("user", userToMap(msg.user))

    val header = Util.buildHeader(MessageNames.USER_JOINED, None)
    Util.buildJson(header, payload)
  }

  private def userToMap(user: User3x): java.util.Map[String, Any] = {

    val wuser = new scala.collection.mutable.HashMap[String, Any]
    wuser += "userid" -> user.id.value
    wuser += "extern_userid" -> user.externalId.value
    wuser += "name" -> user.name.value
    wuser += "role" -> user.roles.toString()
    wuser += "emoji_status" -> user.emojiStatus.value
    wuser += "presenter" -> true
    wuser += "has_stream" -> false
    wuser += "locked" -> false
    wuser += "webcam_stream" -> Array()
    wuser += "phone_user" -> false
    wuser += "listenOnly" -> false

    val vuser = new scala.collection.mutable.HashMap[String, Any]
    vuser += "userid" -> "test-user"
    vuser += "web_userid" -> user.id.value
    vuser += "callername" -> "Caller id name"
    vuser += "callernum" -> "called id num"
    vuser += "joined" -> false
    vuser += "locked" -> false
    vuser += "muted" -> false
    vuser += "talking" -> false

    wuser.put("voiceUser", mapAsJavaMap(vuser))

    mapAsJavaMap(wuser)
  }
}
