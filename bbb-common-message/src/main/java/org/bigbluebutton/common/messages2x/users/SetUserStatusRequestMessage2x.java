package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

// TODO is this actually used? We have setPresenter and we have setEmoji...
public class SetUserStatusRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "SetUserStatusRequestMessage";
    public final Payload payload;

    public SetUserStatusRequestMessage2x(String meetingID, String userID, String status,
                                         String value) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.status = status;
        payload.value = value; //TODO should this be Boolean
    }

    public static SetUserStatusRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SetUserStatusRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String status; //TODO make this enum
        public String value;
    }

}
