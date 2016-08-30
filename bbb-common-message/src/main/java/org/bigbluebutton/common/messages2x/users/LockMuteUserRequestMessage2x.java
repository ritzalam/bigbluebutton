package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class LockMuteUserRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "LockMuteUserRequestMessage";
    public final Payload payload;

    public LockMuteUserRequestMessage2x(String meetingID, String requesterID, String userID,
                                        Boolean lock) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.userID = userID;
        payload.lock = lock;
    }

    public static LockMuteUserRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, LockMuteUserRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String userID;
        public Boolean lock;
    }

}
