package org.bigbluebutton.pubsub.receivers

import com.google.gson.{JsonObject, JsonParser}

class PresentationReceiver() {

  def handleMessage(pattern: String, channel: String, message: String) {
    if (channel.equalsIgnoreCase("bigbluebutton:to-bbb-apps:*")) { //TODO
      val parser: JsonParser = new JsonParser
      val obj: JsonObject = parser.parse(message).asInstanceOf[JsonObject]
      if (obj.has("header") && obj.has("payload")) {
        val header: JsonObject = obj.get("header").asInstanceOf[JsonObject]
        if (header.has("name")) {
          val messageName: String = header.get("name").getAsString
          println("PresentationReceiver got a messageName")
//          if (GetChatHistoryRequestMessage.GET_CHAT_HISTORY_REQUEST == messageName) {
//            val msg: GetChatHistoryRequestMessage = GetChatHistoryRequestMessage.fromJson(message)
//            bbbGW.getChatHistory(msg.meetingId, msg.requesterId, msg.replyTo)
//          }
        }
      }
    }
  }
}
