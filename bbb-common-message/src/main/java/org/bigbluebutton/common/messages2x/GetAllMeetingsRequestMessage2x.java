package org.bigbluebutton.common.messages2x;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetAllMeetingsRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetAllMeetingsRequestMessage";
    public final Payload payload;

    public GetAllMeetingsRequestMessage2x(String meetingID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
    }

    public static GetAllMeetingsRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetAllMeetingsRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
    }

}
