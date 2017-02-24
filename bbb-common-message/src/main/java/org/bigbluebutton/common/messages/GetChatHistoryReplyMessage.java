package org.bigbluebutton.common.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetChatHistoryReplyMessage implements IBigBlueButtonMessage {
  public static final String GET_CHAT_HISTORY_REPLY = "get_chat_history_reply";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String requesterId;
  public final ArrayList<Map<String, Object>> chatHistory;


  public GetChatHistoryReplyMessage(String meetingId, String requesterId, ArrayList<Map<String, Object>> chatHistory) {
    this.meetingId = meetingId;
    this.chatHistory = chatHistory;
    this.requesterId = requesterId;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.CHAT_HISTORY, chatHistory);
    payload.put(MessageBodyConstants.REQUESTER_ID, requesterId);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_CHAT_HISTORY_REPLY, VERSION, null);
    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static GetChatHistoryReplyMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);
    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (GET_CHAT_HISTORY_REPLY.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.CHAT_HISTORY)
                  && payload.has(MessageBodyConstants.REQUESTER_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String requesterId = payload.get(MessageBodyConstants.REQUESTER_ID).getAsString();

            JsonArray history = (JsonArray) payload.get(MessageBodyConstants.CHAT_HISTORY);

            Util util = new Util();

            ArrayList<Map<String, Object>> chatHistory = util.extractChatHistory(history);

            return new GetChatHistoryReplyMessage(meetingId, requesterId, chatHistory);
          }
        }
      }
    }
    return null;
  }
}
