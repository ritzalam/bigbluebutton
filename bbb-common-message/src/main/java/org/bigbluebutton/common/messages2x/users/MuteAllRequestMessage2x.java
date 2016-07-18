package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class MuteAllRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "MuteAllRequestMessage";
    public final Payload payload;

    public MuteAllRequestMessage2x(String meetingID, String requesterID,
                                   Boolean mute) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.mute = mute;
    }

    public static MuteAllRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, MuteAllRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public Boolean mute;
    }

}
