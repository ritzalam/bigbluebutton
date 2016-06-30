package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserLockedMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserLockedMessage";
    public final Payload payload;

    public UserLockedMessage2x(String meetingID, String userID, Boolean locked) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.locked = locked;
    }

    public static UserLockedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserLockedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public Boolean locked;
    }

}
