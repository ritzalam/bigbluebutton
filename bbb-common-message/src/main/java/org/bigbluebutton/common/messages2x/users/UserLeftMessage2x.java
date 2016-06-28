package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.User;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserLeftMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserLeftMessage2x";
    public final Payload payload;

    public UserLeftMessage2x(String meetingID, User user) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.user = user;
    }

    public static UserLeftMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserLeftMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public User user;
    }

}
