package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.User;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;

public class GetUsersReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetUsersReplyMessage";
    public final Payload payload;

    public GetUsersReplyMessage2x(String meetingID, String requesterID, ArrayList<User> users) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.users = users;
    }

    public static GetUsersReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetUsersReplyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public ArrayList<User> users;
    }

}
