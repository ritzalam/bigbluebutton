package org.bigbluebutton.common.messages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GetPresentationInfoReplyMessage implements IBigBlueButtonMessage {
  public static final String GET_PRESENTATION_INFO_REPLY = "get_presentation_info_reply";
  public static final String VERSION = "0.0.1";

  public final String meetingId;
  public final String requesterId;
  public final Map<String, Object> presenter;
  public final ArrayList<Map<String, Object>> presentations;

  public GetPresentationInfoReplyMessage(String meetingId, String requesterId,
                                         Map<String, Object> presenter, ArrayList<Map<String, Object>> presentations) {
    this.meetingId = meetingId;
    this.requesterId = requesterId;
    this.presenter = presenter;
    this.presentations = presentations;
  }

  public String toJson() {
    HashMap<String, Object> payload = new HashMap<String, Object>();
    payload.put(MessageBodyConstants.MEETING_ID, meetingId);
    payload.put(MessageBodyConstants.REQUESTER_ID, requesterId);
    payload.put(MessageBodyConstants.PRESENTER, presenter);
    payload.put(MessageBodyConstants.PRESENTATIONS, presentations);

    java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(GET_PRESENTATION_INFO_REPLY, VERSION, null);

    return MessageBuilder.buildJson(header, payload);
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }


  public static GetPresentationInfoReplyMessage fromJson(String message) {
    JsonParser parser = new JsonParser();
    JsonObject obj = (JsonObject) parser.parse(message);

    if (obj.has("header") && obj.has("payload")) {
      JsonObject header = (JsonObject) obj.get("header");
      JsonObject payload = (JsonObject) obj.get("payload");

      if (header.has("name")) {
        String messageName = header.get("name").getAsString();
        if (GET_PRESENTATION_INFO_REPLY.equals(messageName)) {
          if (payload.has(MessageBodyConstants.MEETING_ID)
                  && payload.has(MessageBodyConstants.REQUESTER_ID)
                  && payload.has(MessageBodyConstants.PRESENTER)
                  && payload.has(MessageBodyConstants.PRESENTATIONS)) {
            String meetingId = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
            String requesterId = payload.get(MessageBodyConstants.REQUESTER_ID).getAsString();
            JsonObject presenterJsonObject = payload.get(MessageBodyConstants.PRESENTER).getAsJsonObject();

            Util util = new Util();
            Map<String, Object> presenter = util.extractCurrentPresenter(presenterJsonObject);

            JsonArray presentationsJsonArray = payload.get(MessageBodyConstants.PRESENTATIONS).getAsJsonArray();
            ArrayList<Map<String, Object>> presentations = util.extractPresentations(presentationsJsonArray);

            return new GetPresentationInfoReplyMessage(meetingId, requesterId, presenter, presentations);
          }
        }
      }
    }
    return null;
  }
}
