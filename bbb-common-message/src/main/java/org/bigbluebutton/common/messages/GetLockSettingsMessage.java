package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetLockSettingsMessage implements IBigBlueButtonMessage {
  public static final String GET_LOCK_SETTINGS = "get_lock_settings";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String userId;

  public GetLockSettingsMessage(String meetingId, String userId) {
    this.meetingId = meetingId;
    this.userId = userId;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.USER_ID, userId);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_LOCK_SETTINGS, VERSION, null);
    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static GetLockSettingsMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (GET_LOCK_SETTINGS.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.USER_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String userId = payload.get(MessageBodyConstants.USER_ID).getAsString();

            return new GetLockSettingsMessage(meetingId, userId);
          }
        }
      }
    }
    return null;
  }
}
