package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendSlideGeneratedMessage implements IBigBlueButtonMessage {
  public static final String SEND_SLIDE_GENERATED = "send_slide_generated";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String messageKey;
  public final String code;
  public final String presId;
  public final int numberOfPages;
  public final int pagesCompleted;
  public final String presName;

  public SendSlideGeneratedMessage(String messageKey, String meetingId,
                                   String code, String presId, int numberOfPages, int pagesCompleted,
                                   String presName) {
    this.meetingId = meetingId;
    this.messageKey = messageKey;
    this.code = code;
    this.presId = presId;
    this.numberOfPages = numberOfPages;
    this.pagesCompleted = pagesCompleted;
    this.presName = presName;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.MESSAGE_KEY, messageKey);
    payload.put(MessageBodyConstants.CODE, code);
    payload.put(MessageBodyConstants.PRESENTATION_ID, presId);
    payload.put(MessageBodyConstants.NUM_PAGES, numberOfPages);
    payload.put(MessageBodyConstants.PAGES_COMPLETED, pagesCompleted);
    payload.put(MessageBodyConstants.PRESENTATION_NAME, presName);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SEND_SLIDE_GENERATED, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    return ChannelConstants.TO_PRESENTATION_CHANNEL;
  }

  public static SendSlideGeneratedMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (SEND_SLIDE_GENERATED.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.MESSAGE_KEY)
                  && payload.has(MessageBodyConstants.CODE)
                  && payload.has(MessageBodyConstants.PRESENTATION_ID)
                  && payload.has(MessageBodyConstants.PAGES_COMPLETED)
                  && payload.has(MessageBodyConstants.NUM_PAGES)
                  && payload.has(MessageBodyConstants.PRESENTATION_NAME)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String messageKey = payload.get(MessageBodyConstants.MESSAGE_KEY).getAsString();
            String code = payload.get(MessageBodyConstants.CODE).getAsString();
            String presId = payload.get(MessageBodyConstants.PRESENTATION_ID).getAsString();
            int numberOfPages = payload.get(MessageBodyConstants.NUM_PAGES).getAsInt();
            int pagesCompleted = payload.get(MessageBodyConstants.PAGES_COMPLETED).getAsInt();
            String presName = payload.get(MessageBodyConstants.PRESENTATION_NAME).getAsString();

            return new SendSlideGeneratedMessage(messageKey, meetingId, code, presId, numberOfPages, pagesCompleted, presName);
          }
        }
      }
    }
    return null;
  }
}
