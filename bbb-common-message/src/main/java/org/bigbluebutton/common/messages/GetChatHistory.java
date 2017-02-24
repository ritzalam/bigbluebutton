package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetChatHistory implements IBigBlueButtonMessage {
  public static final String GET_CHAT_HISTORY_REQUEST = "get_chat_history_request";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String replyTo;
  public final String requesterId;


  public GetChatHistory(String meetingId, String requesterId, String replyTo) {
    this.meetingId = meetingId;
    this.replyTo = replyTo;
    this.requesterId = requesterId;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.REPLY_TO, replyTo);
    payload.put(MessageBodyConstants.REQUESTER_ID, requesterId);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_CHAT_HISTORY_REQUEST, VERSION, null);
    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static GetChatHistory fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (GET_CHAT_HISTORY_REQUEST.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.REPLY_TO)
                  && payload.has(MessageBodyConstants.REQUESTER_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String replyTo = payload.get(MessageBodyConstants.REPLY_TO).getAsString();
            String requesterId = payload.get(MessageBodyConstants.REQUESTER_ID).getAsString();
            return new GetChatHistory(meetingId, replyTo, requesterId);
          }
        }
      }
    }
    return null;
  }
}
