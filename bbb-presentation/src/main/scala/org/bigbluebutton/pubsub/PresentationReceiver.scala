package org.bigbluebutton.pubsub

import com.google.gson.{JsonObject, JsonParser}
import org.bigbluebutton.PresentationManager

class PresentationReceiver(presentationManager: PresentationManager) {

  def handleMessage(pattern: String, channel: String, message: String) {
    if (channel.equalsIgnoreCase("bigbluebutton:to-bbb-apps:presentation")) { //TODO

      println("got message:" + message)
      //TODO consider making the messages follow the proper header/payload format

      val parser: JsonParser = new JsonParser
      val obj: JsonObject = parser.parse(message).asInstanceOf[JsonObject]
      if (obj.has("header") && obj.has("payload")) {
//        val header: JsonObject = obj.get("header").asInstanceOf[JsonObject]
//        if (header.has("name")) {
//          val messageName: String = header.get("name").getAsString
//          println("PresentationReceiver got a messageName " + messageName)
////          if (GetChatHistoryRequestMessage.GET_CHAT_HISTORY_REQUEST == messageName) {
////            val msg: GetChatHistoryRequestMessage = GetChatHistoryRequestMessage.fromJson(message)
////            bbbGW.getChatHistory(msg.meetingId, msg.requesterId, msg.replyTo)
////          }
//        }
      }
      else {
        if (obj.has("messageId")) {
          val messageId: String = obj.get("messageId").getAsString
          if("upload_presentation_message" == messageId) {
            val meetingId = obj.get("meetingId").getAsString
            val presId = obj.get("presId").getAsString
            val presFilename = obj.get("presFilename").getAsString
            val presentationBaseUrl = obj.get("presentationBaseUrl").getAsString
            val fileCompletePath = obj.get("fileCompletePath").getAsString
            presentationManager.handlePresentationUpload(meetingId, presId, presFilename,
              presentationBaseUrl, fileCompletePath)

          }
        }
      }
    }
  }
}
