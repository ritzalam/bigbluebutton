package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetLockSettingsMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetLockSettingsMessage";
    public final Payload payload;

    public GetLockSettingsMessage2x(String meetingID, String userID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
    }

    public static GetLockSettingsMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetLockSettingsMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
    }

}
