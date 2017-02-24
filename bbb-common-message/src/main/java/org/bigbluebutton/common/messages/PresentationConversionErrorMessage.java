package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PresentationConversionErrorMessage implements IBigBlueButtonMessage {
  public static final String PRESENTATION_CONVERSION_ERROR = "presentation_conversion_error_message";
  public final String VERSION = "0.0.1";

  public final String meetingId;
  public final String presentationId;
  public final String code;
  public final String messageKey;
  public final String presentationName;
  public final int numPages;
  public final int maxNumPages;


  public PresentationConversionErrorMessage(String meetingId, String presentationId,
                                            String code, String messageKey, String presentationName,
                                            int numPages, int maxNumPages) {
    this.meetingId = meetingId;
    this.presentationId = presentationId;
    this.code = code;
    this.messageKey = messageKey;
    this.presentationName = presentationName;
    this.maxNumPages = maxNumPages;
    this.numPages = numPages;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.PRESENTATION_ID, presentationId);
    payload.put(MessageBodyConstants.CODE, code);
    payload.put(MessageBodyConstants.MESSAGE_KEY, messageKey);
    payload.put(MessageBodyConstants.PRESENTATION_NAME, presentationName);
    payload.put(MessageBodyConstants.NUM_PAGES, numPages);
    payload.put(MessageBodyConstants.MAX_NUM_PAGES, maxNumPages);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(PRESENTATION_CONVERSION_ERROR, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static PresentationConversionErrorMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (PRESENTATION_CONVERSION_ERROR.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.CODE)
                  && payload.has(MessageBodyConstants.MESSAGE_KEY)
                  && payload.has(MessageBodyConstants.MAX_NUM_PAGES)
                  && payload.has(MessageBodyConstants.NUM_PAGES)
                  && payload.has(MessageBodyConstants.PRESENTATION_NAME)
                  && payload.has(MessageBodyConstants.PRESENTATION_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String presentationId = payload.get(MessageBodyConstants.PRESENTATION_ID).getAsString();
            String presentationName = payload.get(MessageBodyConstants.PRESENTATION_NAME).getAsString();
            String code = payload.get(MessageBodyConstants.CODE).getAsString();
            String messageKey = payload.get(MessageBodyConstants.MESSAGE_KEY).getAsString();
            int numPages = payload.get(MessageBodyConstants.NUM_PAGES).getAsInt();
            int maxNumPages = payload.get(MessageBodyConstants.MAX_NUM_PAGES).getAsInt();

            return new PresentationConversionErrorMessage(meetingId, presentationId,
                    code, messageKey, presentationName, numPages, maxNumPages);
          }
        }
      }
    }
    return null;
  }
}
