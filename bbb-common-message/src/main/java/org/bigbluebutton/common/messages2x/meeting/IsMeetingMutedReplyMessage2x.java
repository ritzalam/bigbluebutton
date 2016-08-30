package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class IsMeetingMutedReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "IsMeetingMutedReplyMessage";
    public final Payload payload;

    public IsMeetingMutedReplyMessage2x(String meetingID, String requesterID, Boolean muted) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.muted = muted;
    }

    public static IsMeetingMutedReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, IsMeetingMutedReplyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public Boolean muted;
    }

}
