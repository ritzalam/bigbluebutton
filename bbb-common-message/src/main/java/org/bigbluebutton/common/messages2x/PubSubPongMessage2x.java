package org.bigbluebutton.common.messages2x;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PubSubPongMessage2x extends AbstractEventMessage {

    public static final String NAME = "PubSubPongMessage2x";
    public final Payload payload;

    public PubSubPongMessage2x(String system, Long timestamp) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.system = system;
        payload.timestamp = timestamp;
    }

    public static PubSubPongMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PubSubPongMessage2x.class);
    }

    public class Payload {
        public String system;
        public Long timestamp;
    }

}
