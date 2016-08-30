package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class LockLayoutRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "LockLayoutRequestMessage";
    public final Payload payload;

    public LockLayoutRequestMessage2x(String meetingID, String userID, Boolean lock,
                                      Boolean viewersOnly) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.lock = lock;
        payload.viewersOnly = viewersOnly;
    }

    public static LockLayoutRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, LockLayoutRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public Boolean lock;
        public Boolean viewersOnly;
    }

}
