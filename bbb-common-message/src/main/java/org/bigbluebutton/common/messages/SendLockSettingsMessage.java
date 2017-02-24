package org.bigbluebutton.common.messages;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class SendLockSettingsMessage implements IBigBlueButtonMessage {
  public static final String SEND_LOCK_SETTINGS = "send_lock_settings";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String userId;
  public final Map<String, Boolean> newSettings;


  public SendLockSettingsMessage(String meetingId, String userId, Map<String, Boolean> newSettings) {
    this.meetingId = meetingId;
    this.userId = userId;
    this.newSettings = newSettings;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();

    Map<String, Boolean> settingsMap = new HashMap<String, Boolean>();

    settingsMap.put(MessageBodyConstants.DISABLE_CAMERA, newSettings.get("disableCam"));
    settingsMap.put(MessageBodyConstants.DISABLE_MICROPHONE, newSettings.get("disableMic"));
    settingsMap.put(MessageBodyConstants.DISABLE_PRIVATE_CHAT, newSettings.get("disablePrivateChat"));
    settingsMap.put(MessageBodyConstants.DISABLE_PUBLIC_CHAT, newSettings.get("disablePublicChat"));
    settingsMap.put(MessageBodyConstants.LOCKED_LAYOUT, newSettings.get("lockedLayout"));
    settingsMap.put(MessageBodyConstants.LOCK_ON_JOIN, newSettings.get("lockOnJoin"));
    settingsMap.put(MessageBodyConstants.LOCK_ON_JOIN_CONFIGURABLE, newSettings.get("lockOnJoinConfigurable"));

    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.USER_ID, userId);
    payload.put(MessageBodyConstants.SETTINGS, settingsMap);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(SEND_LOCK_SETTINGS, VERSION, null);
    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }


  public static SendLockSettingsMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (SEND_LOCK_SETTINGS.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.USER_ID)
                  && payload.has(MessageBodyConstants.SETTINGS)) {

            JsonObject settingsObj = (JsonObject) payload.get(MessageBodyConstants.SETTINGS).getAsJsonObject();
            if (settingsObj.has(MessageBodyConstants.DISABLE_CAMERA)
                    && settingsObj.has(MessageBodyConstants.DISABLE_CAMERA)
                    && settingsObj.has(MessageBodyConstants.DISABLE_MICROPHONE)
                    && settingsObj.has(MessageBodyConstants.DISABLE_PRIVATE_CHAT)
                    && settingsObj.has(MessageBodyConstants.DISABLE_PUBLIC_CHAT)
                    && settingsObj.has(MessageBodyConstants.LOCKED_LAYOUT)
                    && settingsObj.has(MessageBodyConstants.LOCK_ON_JOIN)
                    && settingsObj.has(MessageBodyConstants.LOCK_ON_JOIN_CONFIGURABLE)) {

              Map<String, Boolean> settingsMap = new HashMap<String, Boolean>();

              settingsMap.put("disableCam", settingsObj.get(MessageBodyConstants.DISABLE_CAMERA).getAsBoolean());
              settingsMap.put("disableMic", settingsObj.get(MessageBodyConstants.DISABLE_MICROPHONE).getAsBoolean());
              settingsMap.put("disablePrivateChat", settingsObj.get(MessageBodyConstants.DISABLE_PRIVATE_CHAT).getAsBoolean());
              settingsMap.put("disablePublicChat", settingsObj.get(MessageBodyConstants.DISABLE_PUBLIC_CHAT).getAsBoolean());
              settingsMap.put("lockedLayout", settingsObj.get(MessageBodyConstants.LOCKED_LAYOUT).getAsBoolean());
              settingsMap.put("lockOnJoin", settingsObj.get(MessageBodyConstants.LOCK_ON_JOIN).getAsBoolean());
              settingsMap.put("lockOnJoinConfigurable", settingsObj.get(MessageBodyConstants.LOCK_ON_JOIN_CONFIGURABLE).getAsBoolean());

              String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
              String userId = payload.get(MessageBodyConstants.USER_ID).getAsString();

              return new SendLockSettingsMessage(meetingId, userId, settingsMap);
            }
          }
        }
      }
    }
    return null;
  }
}
