package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class MuteUserRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "MuteUserRequestMessage";
    public final Payload payload;

    public MuteUserRequestMessage2x(String meetingID, String requesterID, String userID,
                                    Boolean mute) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.requesterID = requesterID;
        payload.mute = mute;
    }

    public static MuteUserRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, MuteUserRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String userID;
        public Boolean mute;
    }

}
