package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class GetSlideInfoReplyMessage implements IBigBlueButtonMessage {
  public static final String GET_SLIDE_INFO = "get_slide_info_message";
  public final String VERSION = "0.0.1";

  public final String meetingId;
  public final String requesterId;
  public final int xOffset;
  public final int yOffset;
  public final int widthRatio;
  public final int heightRatio;


  public GetSlideInfoReplyMessage(String meetingId, String requesterId,
                                  int xOffset, int yOffset, int widthRatio, int heightRatio) {
    this.meetingId = meetingId;
    this.requesterId = requesterId;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.widthRatio = widthRatio;
    this.heightRatio = heightRatio;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.REQUESTER_ID, requesterId);
    payload.put(MessageBodyConstants.X_OFFSET, xOffset);
    payload.put(MessageBodyConstants.Y_OFFSET, yOffset);
    payload.put(MessageBodyConstants.WIDTH_RATIO, widthRatio);
    payload.put(MessageBodyConstants.HEIGHT_RATIO, heightRatio);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_SLIDE_INFO, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static GetSlideInfoReplyMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (GET_SLIDE_INFO.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.REQUESTER_ID)
                  && payload.has(MessageBodyConstants.X_OFFSET)
                  && payload.has(MessageBodyConstants.Y_OFFSET)
                  && payload.has(MessageBodyConstants.WIDTH_RATIO)
                  && payload.has(MessageBodyConstants.HEIGHT_RATIO)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String requesterId = payload.get(MessageBodyConstants.REQUESTER_ID).getAsString();
            int xOffset = payload.get(MessageBodyConstants.X_OFFSET).getAsInt();
            int yOffset = payload.get(MessageBodyConstants.Y_OFFSET).getAsInt();
            int widthRatio = payload.get(MessageBodyConstants.WIDTH_RATIO).getAsInt();
            int heightRatio = payload.get(MessageBodyConstants.HEIGHT_RATIO).getAsInt();

            return new GetSlideInfoReplyMessage(meetingId, requesterId,
                    xOffset, yOffset, widthRatio, heightRatio);
          }
        }
      }
    }
    return null;
  }
}
