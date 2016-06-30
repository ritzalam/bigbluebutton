package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class LockUserMessage2x extends AbstractEventMessage {

    public static final String NAME = "LockUserMessage";
    public final Payload payload;

    public LockUserMessage2x(String meetingID, String requesterID, String internalUserID,
                             Boolean lock) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.internalUserID = internalUserID;
        payload.lock = lock;
    }

    public static LockUserMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, LockUserMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String internalUserID;
        public Boolean lock;
    }

}
