package org.bigbluebutton.messages;

import com.google.gson.Gson;
import org.bigbluebutton.messages.body.MessageHeader;
import org.junit.Test;

public class ValidateAuthTokenRequestMessageTest {
  @Test
  public void testValidateAuthTokenRequestMessage() {
    String meetingId = "abc123";
    MessageHeader header =
            new MessageHeader(ValidateAuthTokenRequestMessage.NAME, meetingId, "senderId",
                    "replyTo");
    ValidateAuthTokenRequestMessage.Body body =
            new ValidateAuthTokenRequestMessage.Body("myToken");

    ValidateAuthTokenRequestMessage msg = new ValidateAuthTokenRequestMessage(header, body);
    String json = msg.toJson();
    System.out.println(json);
    
   // ValidateAuthTokenRequest rxMsg = gson.fromJson(json, ValidateAuthTokenRequest.class);
    
   // Assert.assertEquals(rxMsg.payload.meetingId, meetingId);
   // Assert.assertEquals(rxMsg.header.name, ValidateAuthTokenRequest.NAME);
  }
}
