package org.bigbluebutton.common.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

/**
 * Created by anton on 05/02/16.
 */
public class SendStunTurnInfoRequestMessage implements IBigBlueButtonMessage {
  public static final String SEND_STUN_TURN_INFO_REQUEST_MESSAGE = "send_stun_turn_info_request_message";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String requesterId;


  public SendStunTurnInfoRequestMessage(String meetingId, String requesterId) {
    this.meetingId = meetingId;
    this.requesterId = requesterId;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.REQUESTER_ID, requesterId);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SEND_STUN_TURN_INFO_REQUEST_MESSAGE, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static SendStunTurnInfoRequestMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (SEND_STUN_TURN_INFO_REQUEST_MESSAGE.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.REQUESTER_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String requesterId = payload.get(MessageBodyConstants.REQUESTER_ID).getAsString();
            return new SendStunTurnInfoRequestMessage(meetingId, requesterId);
          }
        }
      }
    }
    return null;

  }

}
