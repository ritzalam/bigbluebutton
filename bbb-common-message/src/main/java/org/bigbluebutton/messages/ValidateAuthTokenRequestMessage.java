package org.bigbluebutton.messages;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ValidateAuthTokenRequestMessage {
    public static final String NAME = "ValidateAuthTokenRequestMessage";

    public final MessageHeader header;
    public final Body body;

    public ValidateAuthTokenRequestMessage(MessageHeader header, ValidateAuthTokenRequestMessage.Body body) {
        this.header = header;
        this.body = body;
    }

    public static class Body {
        public final String authToken;

        public Body(String authToken) {
            this.authToken = authToken;
        }
    }

    public String toJson() {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.writeValueAsString(this);
    }

    public static ValidateAuthTokenRequestMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ValidateAuthTokenRequestMessage.class);
    }
}
