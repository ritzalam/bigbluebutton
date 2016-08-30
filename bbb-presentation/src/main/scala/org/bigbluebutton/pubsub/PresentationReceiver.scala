package org.bigbluebutton.pubsub

import com.google.gson.{ JsonObject, JsonParser }
import org.bigbluebutton.PresentationManager
import org.bigbluebutton.common.messages.MessagingConstants

class PresentationReceiver(presentationManager: PresentationManager) {

  def handleMessage(pattern: String, channel: String, message: String) {
    if (channel.equalsIgnoreCase(MessagingConstants.TO_PRESENTATION_CHANNEL)) {

      println("got message:" + message)
      //TODO consider making the messages follow the proper header/payload format

      val parser: JsonParser = new JsonParser
      val obj: JsonObject = parser.parse(message).asInstanceOf[JsonObject]
      if (obj.has("header") && obj.has("payload")) {
        // TODO for the moment the messages exchanged are not of the same type as bbb-common-mesages
      } else {
        if (obj.has("messageId")) {
          val messageId: String = obj.get("messageId").getAsString
          if ("upload_presentation_message" == messageId) {
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
