package org.bigbluebutton.messages;

import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PubSubPingMessage extends AbstractMessage {
    public static final String NAME = "PubSubPingMessage";

    public final MessageHeader header;
    public final Body body;

    public PubSubPingMessage(MessageHeader header, PubSubPingMessage.Body body) {
        super();
        this.header = header;
        this.body = body;
    }

    public static class Body {
        public String system;
        public Long timestamp;

        public Body(String system, Long timestamp) {
            this.system = system;
            this.timestamp = timestamp;
        }
    }

    public static PubSubPingMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PubSubPingMessage.class);
    }
}
