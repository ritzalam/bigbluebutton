package org.bigbluebutton.common.messages2x;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class DestroyMeetingMessage2x extends AbstractEventMessage {

    public static final String NAME = "DestroyMeetingMessage";
    public final Payload payload;

    public DestroyMeetingMessage2x(String meetingID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
    }

    public static DestroyMeetingMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, DestroyMeetingMessage2x.class);
    }

    public class Payload {
        public String meetingID;
    }

}
