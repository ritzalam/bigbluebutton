package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class MeetingMutedMessage2x extends AbstractEventMessage {

    public static final String NAME = "MeetingMutedMessage";
    public final Payload payload;

    public MeetingMutedMessage2x(String meetingID, Boolean muted) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.muted = muted;
    }

    public static MeetingMutedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, MeetingMutedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public Boolean muted;
    }

}
