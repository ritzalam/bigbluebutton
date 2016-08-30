package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserListeningOnlyMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserListeningOnlyMessage";
    public final Payload payload;

    public UserListeningOnlyMessage2x(String meetingID, String userID, Boolean listenOnly) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.listenOnly = listenOnly;
    }

    public static UserListeningOnlyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserListeningOnlyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public Boolean listenOnly;
    }

}
