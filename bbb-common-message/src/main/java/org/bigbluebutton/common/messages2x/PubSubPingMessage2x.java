package org.bigbluebutton.common.messages2x;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PubSubPingMessage2x extends AbstractEventMessage {

    public static final String NAME = "PubSubPingMessage2x";
    public final Payload payload;

    public PubSubPingMessage2x(String system, Long timestamp) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.system = system;
        payload.timestamp = timestamp;
    }

    public static PubSubPingMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PubSubPingMessage2x.class);
    }

    public class Payload {
        public String system;
        public Long timestamp;
    }

}
