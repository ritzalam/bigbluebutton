package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserShareWebcamRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserShareWebcamRequestMessage";
    public final Payload payload;

    public UserShareWebcamRequestMessage2x(String meetingID, String userID, String stream) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.stream = stream;
    }

    public static UserShareWebcamRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserShareWebcamRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String stream;
    }

}
