package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class EditCaptionHistoryMessage implements IBigBlueButtonMessage {
  public static final String EDIT_CAPTION_HISTORY = "edit_caption_history_message";
  public static final String VERSION = "0.0.1";

  public final String meetingID;
  public final String userID;
  public final Integer startIndex;
  public final Integer endIndex;
  public final String locale;
  public final String localeCode;
  public final String text;

  public EditCaptionHistoryMessage(String meetingID, String userID, Integer startIndex, Integer endIndex, String locale, String localeCode, String text) {
    this.meetingID = meetingID;
    this.userID = userID;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.locale = locale;
    this.localeCode = localeCode;
    this.text = text;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingID);
    payload.put(MessageBodyConstants.USER_ID, userID);
    payload.put(MessageBodyConstants.START_INDEX, startIndex);
    payload.put(MessageBodyConstants.END_INDEX, endIndex);
    payload.put(MessageBodyConstants.LOCALE, locale);
    payload.put(MessageBodyConstants.LOCALE_CODE, localeCode);
    payload.put(MessageBodyConstants.TEXT, text);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(EDIT_CAPTION_HISTORY, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static EditCaptionHistoryMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (EDIT_CAPTION_HISTORY.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.USER_ID)
                  && payload.has(MessageBodyConstants.START_INDEX)
                  && payload.has(MessageBodyConstants.END_INDEX)
                  && payload.has(MessageBodyConstants.LOCALE)
                  && payload.has(MessageBodyConstants.LOCALE_CODE)
                  && payload.has(MessageBodyConstants.TEXT)) {
            String meetingID = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String userID = payload.get(MessageBodyConstants.USER_ID).getAsString();
            Integer startIndex = payload.get(MessageBodyConstants.START_INDEX).getAsInt();
            Integer endIndex = payload.get(MessageBodyConstants.END_INDEX).getAsInt();
            String locale = payload.get(MessageBodyConstants.LOCALE).getAsString();
            String localeCode = payload.get(MessageBodyConstants.LOCALE_CODE).getAsString();
            String text = payload.get(MessageBodyConstants.TEXT).getAsString();

            return new EditCaptionHistoryMessage(meetingID, userID, startIndex, endIndex, locale, localeCode, text);
          }
        }
      }
    }
    return null;

  }
}
