package org.bigbluebutton.common.messages;

import com.google.gson.Gson;
import org.bigbluebutton.messages.Header;

public class PubSubPingMessage implements IBigBlueButtonMessage {

  public static final String NAME = "BbbPubSubPingMessage";

  public final Header header;
  public final PubSubPingMessagePayload payload;

  public PubSubPingMessage(PubSubPingMessagePayload payload) {
    this.header = new Header(NAME);
    this.payload = payload;
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  public String getChannel() {
    return MessagingConstants.TO_SYSTEM_CHANNEL;
  }

  public static class PubSubPingMessagePayload {
    public final String system;
    public final Long timestamp;

    public PubSubPingMessagePayload(String system, Long timestamp) {
      this.system = system;
      this.timestamp = timestamp;
    }
  }


}
