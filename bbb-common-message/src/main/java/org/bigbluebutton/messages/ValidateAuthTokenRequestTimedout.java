package org.bigbluebutton.messages;

import com.google.gson.Gson;
import org.bigbluebutton.common.messages.IBigBlueButtonMessage;


public class ValidateAuthTokenRequestTimedout implements IBigBlueButtonMessage {
  public final static String NAME = "ValidateAuthTokenRequestTimedout";
  
  public final Header header;
  public final ValidateAuthTokenRequestTimedoutPayload payload;
  
  public ValidateAuthTokenRequestTimedout(MessageType type, ValidateAuthTokenRequestTimedoutPayload payload) {
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

  static class ValidateAuthTokenRequestTimedoutPayload {
    public final String meetingId;
    public final String userId;
    public final String token;
    
    public ValidateAuthTokenRequestTimedoutPayload(String meetingId, String userId, String token) {
      this.meetingId = meetingId;
      this.userId = userId;
      this.token = token;
    }
  }  
}
