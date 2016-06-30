package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;

public class LockLayoutMessage2x extends AbstractEventMessage {

    public static final String NAME = "LockLayoutMessage";
    public final Payload payload;

    public LockLayoutMessage2x(String meetingID, String setByUserID, Boolean locked,
                               ArrayList<String> users) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.setByUserID = setByUserID;
        payload.locked = locked;
        payload.users = users;
    }

    public static LockLayoutMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, LockLayoutMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String setByUserID;
        public Boolean locked;
        public ArrayList<String> users;
    }

}
