package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendConversionCompletedMessage implements IBigBlueButtonMessage {
  public static final String SEND_CONVERSION_COMPLETED = "send_conversion_completed";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String messageKey;
  public final String code;
  public final String presId;
  public final int numPages;
  public final String presName;
  public final String presBaseUrl;

  public SendConversionCompletedMessage(String messageKey, String meetingId, String code,
                                        String presId, int numPages, String presName, String presBaseUrl) {
    this.meetingId = meetingId;
    this.messageKey = messageKey;
    this.code = code;
    this.presId = presId;
    this.numPages = numPages;
    this.presName = presName;
    this.presBaseUrl = presBaseUrl;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.MESSAGE_KEY, messageKey);
    payload.put(MessageBodyConstants.CODE, code);
    payload.put(MessageBodyConstants.PRESENTATION_ID, presId);
    payload.put(MessageBodyConstants.NUM_PAGES, numPages);
    payload.put(MessageBodyConstants.PRESENTATION_NAME, presName);
    payload.put(MessageBodyConstants.PRESENTATION_BASE_URL, presBaseUrl);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SEND_CONVERSION_COMPLETED, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static SendConversionCompletedMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (SEND_CONVERSION_COMPLETED.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.MESSAGE_KEY)
                  && payload.has(MessageBodyConstants.CODE)
                  && payload.has(MessageBodyConstants.PRESENTATION_ID)
                  && payload.has(MessageBodyConstants.NUM_PAGES)
                  && payload.has(MessageBodyConstants.PRESENTATION_NAME)
                  && payload.has(MessageBodyConstants.PRESENTATION_BASE_URL)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String messageKey = payload.get(MessageBodyConstants.MESSAGE_KEY).getAsString();
            String code = payload.get(MessageBodyConstants.CODE).getAsString();
            String presId = payload.get(MessageBodyConstants.PRESENTATION_ID).getAsString();
            int numPages = payload.get(MessageBodyConstants.NUM_PAGES).getAsInt();
            String presName = payload.get(MessageBodyConstants.PRESENTATION_NAME).getAsString();
            String presBaseUrl = payload.get(MessageBodyConstants.PRESENTATION_BASE_URL).getAsString();

            return new SendConversionCompletedMessage(messageKey, meetingId, code, presId, numPages, presName, presBaseUrl);
          }
        }
      }
    }
    return null;
  }
}
