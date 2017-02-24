package org.bigbluebutton.common.messages;

import java.util.HashMap;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RequestWhiteboardAnnotationHistoryRequestMessage implements IBigBlueButtonMessage {
  public static final String REQUEST_WHITEBOARD_ANNOTATION_HISTORY_REQUEST = "request_whiteboard_annotation_history_request";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String whiteboardId;
  public final String requesterId;
  public final String replyTo;


  public RequestWhiteboardAnnotationHistoryRequestMessage(String meetingId,
                                                          String requesterId, String whiteboardId, String replyTo) {
    this.meetingId = meetingId;
    this.whiteboardId = whiteboardId;
    this.requesterId = requesterId;
    this.replyTo = replyTo;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.WHITEBOARD_ID, whiteboardId);
    payload.put(MessageBodyConstants.REQUESTER_ID, requesterId);
    payload.put(MessageBodyConstants.REPLY_TO, replyTo);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(REQUEST_WHITEBOARD_ANNOTATION_HISTORY_REQUEST, VERSION, null);
    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static RequestWhiteboardAnnotationHistoryRequestMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);
    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (REQUEST_WHITEBOARD_ANNOTATION_HISTORY_REQUEST.equals(messageName)) {

          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.WHITEBOARD_ID)
                  && payload.has(MessageBodyConstants.REPLY_TO)
                  && payload.has(MessageBodyConstants.REQUESTER_ID)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String whiteboardId = payload.get(MessageBodyConstants.WHITEBOARD_ID).getAsString();
            String requesterId = payload.get(MessageBodyConstants.REQUESTER_ID).getAsString();
            String replyTo = payload.get(MessageBodyConstants.REPLY_TO).getAsString();

            return new RequestWhiteboardAnnotationHistoryRequestMessage(meetingId, requesterId, whiteboardId, replyTo);
          }
        }
      }
    }
    return null;
  }
}
