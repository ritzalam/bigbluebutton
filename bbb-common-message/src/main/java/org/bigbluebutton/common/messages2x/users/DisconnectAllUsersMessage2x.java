package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class DisconnectAllUsersMessage2x extends AbstractEventMessage {

    public static final String NAME = "DisconnectAllUsersMessage";
    public final Payload payload;

    public DisconnectAllUsersMessage2x(String meetingID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
    }

    public static DisconnectAllUsersMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, DisconnectAllUsersMessage2x.class);
    }

    public class Payload {
        public String meetingID;
    }

}
