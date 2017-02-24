package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ValidateAuthTokenMessage implements IBigBlueButtonMessage {
  public static final String VALIDATE_AUTH_TOKEN = "validate_auth_token";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String userId;
  public final String token;
  public final String replyTo;
  public final String sessionId;

  public ValidateAuthTokenMessage(String meetingId, String userId, String token, String replyTo, String sessionId) {
    this.meetingId = meetingId;
    this.userId = userId;
    this.token = token;
    this.replyTo = replyTo;
    this.sessionId = sessionId;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.USER_ID, userId);
    payload.put(MessageBodyConstants.AUTH_TOKEN, token);
    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(VALIDATE_AUTH_TOKEN, VERSION, replyTo);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static ValidateAuthTokenMessage fromJson(String message) {

    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (VALIDATE_AUTH_TOKEN.equals(messageName)) {

          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.USER_ID)
                  && payload.has(MessageBodyConstants.AUTH_TOKEN)
                  && header.has(MessageBodyConstants.REPLY_TO)) {

            String id = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String userid = payload.get(MessageBodyConstants.USER_ID).getAsString();
            String authToken = payload.get(MessageBodyConstants.AUTH_TOKEN).getAsString();
            String replyTo = header.get(MessageBodyConstants.REPLY_TO).getAsString();
            String sessionId = "tobeimplemented";
            return new ValidateAuthTokenMessage(id, userid, authToken, replyTo,
                    sessionId);
          }
        }
      }
    }

    return null;

  }
}
