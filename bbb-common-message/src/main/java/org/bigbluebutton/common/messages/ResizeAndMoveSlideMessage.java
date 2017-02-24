package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ResizeAndMoveSlideMessage implements IBigBlueButtonMessage {
  public static final String RESIZE_AND_MOVE_SLIDE = "resize_and_move_slide";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final double xOffset;
  public final double yOffset;
  public final double widthRatio;
  public final double heightRatio;

  public ResizeAndMoveSlideMessage(String meetingId, double xOffset, double yOffset,
                                   double widthRatio, double heightRatio) {
    this.meetingId = meetingId;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.heightRatio = heightRatio;
    this.widthRatio = widthRatio;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.X_OFFSET, xOffset);
    payload.put(MessageBodyConstants.Y_OFFSET, yOffset);
    payload.put(MessageBodyConstants.HEIGHT_RATIO, heightRatio);
    payload.put(MessageBodyConstants.WIDTH_RATIO, widthRatio);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(RESIZE_AND_MOVE_SLIDE, VERSION, null);
    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static ResizeAndMoveSlideMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (RESIZE_AND_MOVE_SLIDE.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.X_OFFSET)
                  && payload.has(MessageBodyConstants.Y_OFFSET)
                  && payload.has(MessageBodyConstants.HEIGHT_RATIO)
                  && payload.has(MessageBodyConstants.WIDTH_RATIO)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            double xOffset = payload.get(MessageBodyConstants.X_OFFSET).getAsDouble();
            double yOffset = payload.get(MessageBodyConstants.Y_OFFSET).getAsDouble();
            double heightRatio = payload.get(MessageBodyConstants.HEIGHT_RATIO).getAsDouble();
            double widthRatio = payload.get(MessageBodyConstants.WIDTH_RATIO).getAsDouble();

            return new ResizeAndMoveSlideMessage(meetingId, xOffset, yOffset, widthRatio, heightRatio);
          }
        }
      }
    }
    return null;
  }
}
