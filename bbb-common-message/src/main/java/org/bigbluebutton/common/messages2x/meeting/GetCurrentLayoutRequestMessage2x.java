package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetCurrentLayoutRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetCurrentLayoutRequestMessage";
    public final Payload payload;

    public GetCurrentLayoutRequestMessage2x(String meetingID, String userID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
    }

    public static GetCurrentLayoutRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetCurrentLayoutRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
    }

}
