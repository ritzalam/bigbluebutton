package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class EndMeetingMessage2x extends AbstractEventMessage {

    public static final String NAME = "EndMeetingMessage";
    public final Payload payload;

    public EndMeetingMessage2x(String meetingID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
    }

    public static EndMeetingMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, EndMeetingMessage2x.class);
    }

    public class Payload {
        public String meetingID;
    }

}
