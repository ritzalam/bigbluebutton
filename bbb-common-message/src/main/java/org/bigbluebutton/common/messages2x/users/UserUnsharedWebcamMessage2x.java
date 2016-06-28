package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserUnsharedWebcamMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserUnsharedWebcamMessage";
    public final Payload payload;

    public UserUnsharedWebcamMessage2x(String meetingID, String userID, String stream) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.stream = stream;
    }

    public static UserUnsharedWebcamMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserUnsharedWebcamMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String stream;
    }

}
