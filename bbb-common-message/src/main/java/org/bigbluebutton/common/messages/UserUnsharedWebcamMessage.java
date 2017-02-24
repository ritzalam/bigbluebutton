package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class UserUnsharedWebcamMessage implements IBigBlueButtonMessage {
  public static final String USER_UNSHARED_WEBCAM = "user_unshared_webcam_message";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String userId;
  public final String stream;

  public UserUnsharedWebcamMessage(String meetingId, String userId, String stream) {
    this.meetingId = meetingId;
    this.userId = userId;
    this.stream = stream;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.USER_ID, userId);
    payload.put(MessageBodyConstants.STREAM, stream);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(USER_UNSHARED_WEBCAM, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static UserUnsharedWebcamMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (USER_UNSHARED_WEBCAM.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.USER_ID)
                  && payload.has(MessageBodyConstants.STREAM)) {
            String id = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String userid = payload.get(MessageBodyConstants.USER_ID).getAsString();
            String stream = payload.get(MessageBodyConstants.STREAM).getAsString();
            return new UserUnsharedWebcamMessage(id, userid, stream);
          }
        }
      }
    }
    return null;

  }
}
