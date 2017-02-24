package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AssignPresenterRequestMessage implements IBigBlueButtonMessage {
  public static final String ASSIGN_PRESENTER_REQUEST = "assign_presenter_request_message";
  public final String VERSION = "0.0.1";

  public final String meetingId;
  public final String newPresenterId;
  public final String newPresenterName;
  public final String assignedBy;

  public AssignPresenterRequestMessage(String meetingID, String newPresenterId, String newPresenterName, String assignedBy) {
    this.meetingId = meetingID;
    this.newPresenterId = newPresenterId;
    this.newPresenterName = newPresenterName;
    this.assignedBy = assignedBy;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.NEW_PRESENTER_ID, newPresenterId);
    payload.put(MessageBodyConstants.NEW_PRESENTER_NAME, newPresenterName);
    payload.put(MessageBodyConstants.ASSIGNED_BY, assignedBy);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(ASSIGN_PRESENTER_REQUEST, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static AssignPresenterRequestMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (ASSIGN_PRESENTER_REQUEST.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.NEW_PRESENTER_ID)
                  && payload.has(MessageBodyConstants.NEW_PRESENTER_NAME)
                  && payload.has(MessageBodyConstants.ASSIGNED_BY)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String newPresenterId = payload.get(MessageBodyConstants.NEW_PRESENTER_ID).getAsString();
            String newPresenterName = payload.get(MessageBodyConstants.NEW_PRESENTER_NAME).getAsString();
            String assignedBy = payload.get(MessageBodyConstants.ASSIGNED_BY).getAsString();

            return new AssignPresenterRequestMessage(meetingId, newPresenterId, newPresenterName, assignedBy);
          }
        }
      }
    }

    return null;
  }
}
