package org.bigbluebutton.messages;

import com.google.gson.Gson;
import org.bigbluebutton.common.messages.IBigBlueButtonMessage;

public class ValidateAuthTokenReply implements IBigBlueButtonMessage {
  public final static String NAME = "ValidateAuthTokenReply";
  
  public final Header header;
  public final ValidateAuthTokenReplyPayload payload;
  
  public ValidateAuthTokenReply(MessageType type, ValidateAuthTokenReplyPayload payload) {
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

  static class ValidateAuthTokenReplyPayload {
    public final String meetingId;
    public final String userId;
    public final String token;
    public final Boolean valid;
    
    public ValidateAuthTokenReplyPayload(String meetingId, String userId, String token, Boolean valid) {
      this.meetingId = meetingId;
      this.userId = userId;
      this.token = token;
      this.valid = valid;
    }
  }
}
