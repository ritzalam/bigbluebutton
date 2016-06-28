package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserLeavingMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserLeavingMessage2x";
    public final Payload payload;

    public UserLeavingMessage2x(String meetingID, String userID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
    }

    public static UserLeavingMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserLeavingMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
    }

}
