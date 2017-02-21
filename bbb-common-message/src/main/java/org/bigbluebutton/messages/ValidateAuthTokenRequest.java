package org.bigbluebutton.messages;

import com.google.gson.Gson;
import org.bigbluebutton.common.messages.IBigBlueButtonMessage;

public class ValidateAuthTokenRequest implements IBigBlueButtonMessage {
  public final static String NAME = "ValidateAuthTokenRequest";
  
  public final Header header;
  public final ValidateAuthTokenRequestPayload payload;
  
  public ValidateAuthTokenRequest(MessageType type, ValidateAuthTokenRequestPayload payload) {
    this.header = new Header(NAME);
    this.payload = payload;
  }

  public String getChannel() {
    // TODO
    return "FIX MEE!!!";
  }

  public String toJson() {
    Gson gson = new Gson();
    return gson.toJson(this);
  }

  static class ValidateAuthTokenRequestPayload {

    public final String meetingId;
    public final String userId;
    public final String token;
    
    public ValidateAuthTokenRequestPayload(String meetingId, String userId, String token) {
      this.meetingId = meetingId;
      this.userId = userId;
      this.token = token;    
    }
  }
}
