package org.bigbluebutton.messages;

import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class KeepAliveMessage extends AbstractMessage {
    public static final String NAME = "KeepAliveMessage";

    public final MessageHeader header;
    public final Body body;

    public KeepAliveMessage(MessageHeader header, KeepAliveMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static KeepAliveMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, KeepAliveMessage.class);
    }

    public static class Body {
        public String aliveID;

        public Body(String aliveID) {
            this.aliveID = aliveID;
        }
    }

}
