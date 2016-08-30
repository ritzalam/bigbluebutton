package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class MeetingHasEndedMessage2x extends AbstractEventMessage {

    public static final String NAME = "MeetingHasEndedMessage";
    public final Payload payload;

    public MeetingHasEndedMessage2x(String meetingID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
    }

    public static MeetingHasEndedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, MeetingHasEndedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
    }

}
