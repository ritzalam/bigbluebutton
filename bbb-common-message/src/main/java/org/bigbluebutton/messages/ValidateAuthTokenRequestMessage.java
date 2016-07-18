package org.bigbluebutton.messages;

import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ValidateAuthTokenRequestMessage extends AbstractMessage {
    public static final String NAME = "ValidateAuthTokenRequestMessage";

    public final MessageHeader header;
    public final Body body;

    public ValidateAuthTokenRequestMessage(MessageHeader header, ValidateAuthTokenRequestMessage
            .Body body) {
        super();
        this.header = header;
        this.body = body;
    }

    public static class Body {
        public final String authToken;

        public Body(String authToken) {
            this.authToken = authToken;
        }
    }

    public static ValidateAuthTokenRequestMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ValidateAuthTokenRequestMessage.class);
    }
}
