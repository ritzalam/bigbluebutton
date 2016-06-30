package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;

public class BroadcastLayoutRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "BroadcastLayoutRequestMessage";
    public final Payload payload;

    public BroadcastLayoutRequestMessage2x(String meetingID, String userID, String layout) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.layout = layout;
    }

    public static BroadcastLayoutRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, BroadcastLayoutRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String layout;
    }

}
