package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RemovePresentationMessage implements IBigBlueButtonMessage {
  public static final String REMOVE_PRESENTATION = "remove_presentation";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String presentationId;

  public RemovePresentationMessage(String meetingId, String presentationId) {
    this.meetingId = meetingId;
    this.presentationId = presentationId;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.PRESENTATION_ID, presentationId);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(REMOVE_PRESENTATION, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static RemovePresentationMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (REMOVE_PRESENTATION.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.PRESENTATION_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String presentationId = payload.get(MessageBodyConstants.PRESENTATION_ID).getAsString();

            return new RemovePresentationMessage(meetingId, presentationId);
          }
        }
      }
    }
    return null;
  }
}
