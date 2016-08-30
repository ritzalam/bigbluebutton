package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendStunTurnInfoRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendStunTurnInfoRequestMessage";
    public final Payload payload;

    public SendStunTurnInfoRequestMessage2x(String meetingID, String requesterID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
    }

    public static SendStunTurnInfoRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendStunTurnInfoRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
    }

}
