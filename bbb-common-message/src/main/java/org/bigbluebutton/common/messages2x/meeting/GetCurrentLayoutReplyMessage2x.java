package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetCurrentLayoutReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetCurrentLayoutReplyMessage";
    public final Payload payload;

    public GetCurrentLayoutReplyMessage2x(String meetingID, String requesterID, String
            setByUserID, String layout, Boolean locked) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.setByUserID = setByUserID;
        payload.layout = layout;
        payload.locked = locked;
    }

    public static GetCurrentLayoutReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetCurrentLayoutReplyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String setByUserID;
        public String layout;
        public Boolean locked;
    }

}
