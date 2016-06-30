package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;

public class BroadcastLayoutMessage2x extends AbstractEventMessage {

    public static final String NAME = "BroadcastLayoutMessage";
    public final Payload payload;

    public BroadcastLayoutMessage2x(String meetingID, String setByUserID, String layout,
                                    Boolean locked, ArrayList<String> users) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.setByUserID = setByUserID;
        payload.layout = layout;
        payload.locked = locked;
        payload.users = users;
    }

    public static BroadcastLayoutMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, BroadcastLayoutMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String setByUserID;
        public String layout;
        public Boolean locked;
        public ArrayList<String> users;
    }

}
