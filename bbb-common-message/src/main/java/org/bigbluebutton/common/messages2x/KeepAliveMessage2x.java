package org.bigbluebutton.common.messages2x;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class KeepAliveMessage2x extends AbstractEventMessage {

    public static final String NAME = "KeepAliveMessage";
    public final Payload payload;

    public KeepAliveMessage2x(String keepAliveID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.keepAliveID = keepAliveID;
    }

    public static KeepAliveMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, KeepAliveMessage2x.class);
    }

    public class Payload {
        public String keepAliveID;
    }

}
