package org.bigbluebutton.common.messages;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;

public class MeetingDestroyedMessage implements IBigBlueButtonMessage {
  public static final String NAME = "meeting_destroyed_event";
  public final String VERSION = "0.0.1";

  public final String meetingId;

  public MeetingDestroyedMessage(String meetingID) {
    this.meetingId = meetingID;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);

    HashMap<String, Object> header = MessageBuilder.buildHeader(NAME, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static MeetingDestroyedMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (NAME.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();

            return new MeetingDestroyedMessage(meetingId);
          }
        }
      }
    }

    return null;
  }
}
