package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GoToSlideMessage implements IBigBlueButtonMessage {
  public static final String GO_TO_SLIDE = "go_to_slide";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String page;

  public GoToSlideMessage(String meetingId, String page) {
    this.meetingId = meetingId;
    this.page = page;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.PAGE, page);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GO_TO_SLIDE, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static GoToSlideMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (GO_TO_SLIDE.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.PAGE)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String page = payload.get(MessageBodyConstants.PAGE).getAsString();

            return new GoToSlideMessage(meetingId, page);
          }
        }
      }
    }
    return null;
  }
}
