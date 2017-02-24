package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class UpdateCaptionOwnerMessage implements IBigBlueButtonMessage {
  public static final String UPDATE_CAPTION_OWNER = "update_caption_owner_message";
  public static final String VERSION = "0.0.1";

  public final String meetingID;
  public final String locale;
  public final String localeCode;
  public final String ownerID;

  public UpdateCaptionOwnerMessage(String meetingID, String locale, String localeCode, String ownerID) {
    this.meetingID = meetingID;
    this.locale = locale;
    this.localeCode = localeCode;
    this.ownerID = ownerID;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingID);
    payload.put(MessageBodyConstants.LOCALE, locale);
    payload.put(MessageBodyConstants.LOCALE_CODE, localeCode);
    payload.put(MessageBodyConstants.OWNER_ID, ownerID);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(UPDATE_CAPTION_OWNER, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static UpdateCaptionOwnerMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (UPDATE_CAPTION_OWNER.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.LOCALE)
                  && payload.has(MessageBodyConstants.LOCALE_CODE)
                  && payload.has(MessageBodyConstants.OWNER_ID)) {
            String meetingID = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String locale = payload.get(MessageBodyConstants.LOCALE).getAsString();
            String localeCode = payload.get(MessageBodyConstants.LOCALE_CODE).getAsString();
            String ownerID = payload.get(MessageBodyConstants.OWNER_ID).getAsString();

            return new UpdateCaptionOwnerMessage(meetingID, locale, localeCode, ownerID);
          }
        }
      }
    }
    return null;

  }
}
