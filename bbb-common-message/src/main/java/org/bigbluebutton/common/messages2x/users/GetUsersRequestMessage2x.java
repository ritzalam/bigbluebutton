package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetUsersRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetUsersRequestMessage";
    public final Payload payload;

    public GetUsersRequestMessage2x(String meetingID, String requesterID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
    }

    public static GetUsersRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetUsersRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
    }

}
