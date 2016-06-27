package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserEjectedFromMeetingMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserEjectedFromMeetingMessage";
    public final Payload payload;

    public UserEjectedFromMeetingMessage2x(String meetingID, String userID, String ejectedBy) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.ejectedBy = ejectedBy;
    }

    public static UserEjectedFromMeetingMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserEjectedFromMeetingMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String ejectedBy;
    }

}
