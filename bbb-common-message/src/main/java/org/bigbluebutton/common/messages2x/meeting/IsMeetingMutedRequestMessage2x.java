package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class IsMeetingMutedRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "IsMeetingMutedRequestMessage";
    public final Payload payload;

    public IsMeetingMutedRequestMessage2x(String meetingID, String requesterID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
    }

    public static IsMeetingMutedRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, IsMeetingMutedRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
    }

}
