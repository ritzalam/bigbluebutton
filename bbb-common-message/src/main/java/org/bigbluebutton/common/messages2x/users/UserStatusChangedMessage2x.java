package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserStatusChangedMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserStatusChangedMessage";
    public final Payload payload;

    public UserStatusChangedMessage2x(String meetingID, String userID, String status,
                                      String value) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.status = status;
        payload.value = value;
    }

    public static UserStatusChangedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserStatusChangedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String status; //TODO should this be Role enum?
        public String value;
    }

}
