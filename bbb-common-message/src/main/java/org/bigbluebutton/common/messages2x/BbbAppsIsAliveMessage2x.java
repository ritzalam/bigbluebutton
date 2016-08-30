package org.bigbluebutton.common.messages2x;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class BbbAppsIsAliveMessage2x extends AbstractEventMessage {

    public static final String NAME = "BbbAppsIsAliveMessage";
    public final Payload payload;

    public BbbAppsIsAliveMessage2x(Long startedOn, Long timestamp) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.startedOn = startedOn;
        payload.timestamp = timestamp;
    }

    public static BbbAppsIsAliveMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, BbbAppsIsAliveMessage2x.class);
    }

    public class Payload {
        public Long startedOn;
        public Long timestamp;
    }

}
