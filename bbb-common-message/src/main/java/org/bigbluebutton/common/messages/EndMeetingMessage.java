package org.bigbluebutton.common.messages;

import com.google.gson.Gson;
import org.bigbluebutton.messages.Header;

public class EndMeetingMessage implements IBigBlueButtonMessage {
  public static final String NAME = "end_meeting_request_event";

  public final Header header;
  public final EndMeetingMessagePayload payload;

  public EndMeetingMessage(EndMeetingMessagePayload payload) {
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

  public static class EndMeetingMessagePayload {
    public final String id;

    public EndMeetingMessagePayload(String id) {
      this.id = id;
    }
  }

}
