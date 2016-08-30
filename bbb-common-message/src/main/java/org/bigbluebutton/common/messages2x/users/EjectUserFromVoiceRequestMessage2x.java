package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class EjectUserFromVoiceRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "EjectUserFromVoiceRequestMessage2x";
    public final Payload payload;

    public EjectUserFromVoiceRequestMessage2x(String meetingID,
                                              String userID, String requesterID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.requesterID = requesterID;
    }

    public static EjectUserFromVoiceRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, EjectUserFromVoiceRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public String requesterID;
    }

}
