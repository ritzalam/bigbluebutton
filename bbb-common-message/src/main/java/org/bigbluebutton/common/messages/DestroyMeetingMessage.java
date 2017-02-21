package org.bigbluebutton.common.messages;

import com.google.gson.Gson;
import org.bigbluebutton.messages.Header;

public class DestroyMeetingMessage implements IBigBlueButtonMessage {
  public static final String NAME = "destroy_meeting_request_event";

  public final Header header;
  public final DestroyMeetingMessagePayload payload;

  public DestroyMeetingMessage(DestroyMeetingMessagePayload payload) {
    this.header = new Header(NAME);
    this.payload = payload;
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public String getChannel() {
    return MessagingConstants.TO_MEETING_CHANNEL;
  }

  public static class DestroyMeetingMessagePayload {
    public final String id;

    public DestroyMeetingMessagePayload(String id) {
      this.id = id;
    }
  }
}
