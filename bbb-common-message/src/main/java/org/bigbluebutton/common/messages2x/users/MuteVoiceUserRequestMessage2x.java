package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class MuteVoiceUserRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "MuteVoiceUserRequestMessage";
    public final Payload payload;

    public MuteVoiceUserRequestMessage2x(String meetingID, String requesterID, String userID,
                                         Boolean mute) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.requesterID = requesterID;
        payload.mute = mute;
    }

    public static MuteVoiceUserRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, MuteVoiceUserRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String userID;
        public Boolean mute;
    }

}
