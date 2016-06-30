package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserMutedInVoiceConfMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserMutedInVoiceConfMessage";
    public final Payload payload;

    public UserMutedInVoiceConfMessage2x(String voiceConf, String voiceUserID, Boolean muted) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.voiceConf = voiceConf;
        payload.voiceUserID = voiceUserID;
        payload.muted = muted;
    }

    public static UserMutedInVoiceConfMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserMutedInVoiceConfMessage2x.class);
    }

    public class Payload {
        public String voiceConf;
        public String voiceUserID;
        public Boolean muted;
    }

}
