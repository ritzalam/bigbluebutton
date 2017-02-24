package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendPageCountErrorMessage implements IBigBlueButtonMessage {
  public static final String SEND_PAGE_COUNT_ERROR = "send_page_count_error";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String messageKey;
  public final String code;
  public final String presId;
  public final int numberOfPages;
  public final int maxNumberPages;
  public final String presName;

  public SendPageCountErrorMessage(String messageKey, String meetingId,
                                   String code, String presId, int numberOfPages, int maxNumberPages,
                                   String presName) {
    this.meetingId = meetingId;
    this.messageKey = messageKey;
    this.code = code;
    this.presId = presId;
    this.numberOfPages = numberOfPages;
    this.maxNumberPages = maxNumberPages;
    this.presName = presName;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.MESSAGE_KEY, messageKey);
    payload.put(MessageBodyConstants.CODE, code);
    payload.put(MessageBodyConstants.PRESENTATION_ID, presId);
    payload.put(MessageBodyConstants.NUM_PAGES, numberOfPages);
    payload.put(MessageBodyConstants.MAX_NUM_PAGES, maxNumberPages);
    payload.put(MessageBodyConstants.PRESENTATION_NAME, presName);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SEND_PAGE_COUNT_ERROR, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    return ChannelConstants.TO_PRESENTATION_CHANNEL;
  }

  public static SendPageCountErrorMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (SEND_PAGE_COUNT_ERROR.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.MESSAGE_KEY)
                  && payload.has(MessageBodyConstants.CODE)
                  && payload.has(MessageBodyConstants.PRESENTATION_ID)
                  && payload.has(MessageBodyConstants.MAX_NUM_PAGES)
                  && payload.has(MessageBodyConstants.NUM_PAGES)
                  && payload.has(MessageBodyConstants.PRESENTATION_NAME)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String messageKey = payload.get(MessageBodyConstants.MESSAGE_KEY).getAsString();
            String code = payload.get(MessageBodyConstants.CODE).getAsString();
            String presId = payload.get(MessageBodyConstants.PRESENTATION_ID).getAsString();
            int numberOfPages = payload.get(MessageBodyConstants.NUM_PAGES).getAsInt();
            int maxNumberPages = payload.get(MessageBodyConstants.MAX_NUM_PAGES).getAsInt();
            String presName = payload.get(MessageBodyConstants.PRESENTATION_NAME).getAsString();

            return new SendPageCountErrorMessage(messageKey, meetingId, code, presId, numberOfPages, maxNumberPages, presName);
          }
        }
      }
    }
    return null;
  }
}
