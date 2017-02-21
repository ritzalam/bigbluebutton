package org.bigbluebutton.common.messages;

import org.bigbluebutton.messages.Header;

public class KeepAliveMessage implements IBigBlueButtonMessage {
  public static final String NAME = "keep_alive_request";

  public final Header header;
  public final KeepAliveMessagePayload payload;

  public KeepAliveMessage(KeepAliveMessagePayload payload) {
    this.header = new Header(NAME);
    this.payload = payload;
  }

  public String toJson() {
    // TODO
    return "FIX MEE!!!";
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public static class KeepAliveMessagePayload {
    public final String id;

    public KeepAliveMessagePayload(String id) {
      this.id = id;
    }
  }

}
