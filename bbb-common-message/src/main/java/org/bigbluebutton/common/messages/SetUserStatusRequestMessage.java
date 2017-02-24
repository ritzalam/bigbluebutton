package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class SetUserStatusRequestMessage implements IBigBlueButtonMessage {
  public static final String SET_USER_STATUS_REQUEST = "set_user_status_request_message";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String userId;
  public final String status;
  public final String value;

  public SetUserStatusRequestMessage(String meetingId, String userId, String status, String value) {
    this.meetingId = meetingId;
    this.userId = userId;
    this.status = status;
    this.value = value;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.USER_ID, userId);
    payload.put(MessageBodyConstants.STATUS, status);
    payload.put(MessageBodyConstants.VALUE, value);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SET_USER_STATUS_REQUEST, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }


  public static SetUserStatusRequestMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (SET_USER_STATUS_REQUEST.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.USER_ID)
                  && payload.has(MessageBodyConstants.STATUS)
                  && payload.has(MessageBodyConstants.VALUE)) {
            String id = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String userid = payload.get(MessageBodyConstants.USER_ID).getAsString();
            String status = payload.get(MessageBodyConstants.STATUS).getAsString();
            String value = payload.get(MessageBodyConstants.VALUE).getAsString();
            return new SetUserStatusRequestMessage(id, userid, status, value);
          }
        }
      }
    }
    return null;

  }
}
