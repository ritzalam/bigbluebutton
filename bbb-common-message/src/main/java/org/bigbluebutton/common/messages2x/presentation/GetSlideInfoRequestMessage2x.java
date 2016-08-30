package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetSlideInfoRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetSlideInfoRequestMessage2x";
    public final GetSlideInfoRequestMessage2x.Payload payload;

    public GetSlideInfoRequestMessage2x(String meetingID, String requesterID, String replyTo) {
        super();
        header.name = NAME;

        this.payload = new GetSlideInfoRequestMessage2x.Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.replyTo = replyTo;
    }

    public static GetSlideInfoRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetSlideInfoRequestMessage2x.class);
    }

    public class Payload {
        public String replyTo;
        public String meetingID;
        public String requesterID;
    }
}
