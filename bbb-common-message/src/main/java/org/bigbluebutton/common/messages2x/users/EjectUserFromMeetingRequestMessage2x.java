package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class EjectUserFromMeetingRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "EjectUserFromMeetingRequestMessage";
    public final Payload payload;

    public EjectUserFromMeetingRequestMessage2x(String meetingID, String userID, String ejectedBy) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.ejectedBy = ejectedBy;
    }

    public static EjectUserFromMeetingRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, EjectUserFromMeetingRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String ejectedBy;
    }

}
