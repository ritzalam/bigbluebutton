package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MeetingMutedMessage implements IBigBlueButtonMessage {
  public static final String MEETING_MUTED = "meeting_muted_message";
  public final String VERSION = "0.0.1";

  public final String meetingId;
  public final Boolean muted;

  public MeetingMutedMessage(String meetingID, Boolean muted) {
    this.meetingId = meetingID;
    this.muted = muted;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(MEETING_MUTED, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static MeetingMutedMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (MEETING_MUTED.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.MEETING_MUTED)) {
            String meetingID = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            Boolean muted = payload.get(MessageBodyConstants.MEETING_MUTED).getAsBoolean();

            return new MeetingMutedMessage(meetingID, muted);
          }
        }
      }
    }

    return null;
  }
}
