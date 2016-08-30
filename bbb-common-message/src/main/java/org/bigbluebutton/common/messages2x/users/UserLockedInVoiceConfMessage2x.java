package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserLockedInVoiceConfMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserLockedInVoiceConfMessage";
    public final Payload payload;

    public UserLockedInVoiceConfMessage2x(String voiceConf, String voiceUserID, Boolean locked) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.voiceConf = voiceConf;
        payload.voiceUserID = voiceUserID;
        payload.locked = locked;
    }

    public static UserLockedInVoiceConfMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserLockedInVoiceConfMessage2x.class);
    }

    public class Payload {
        public String voiceConf;
        public String voiceUserID;
        public Boolean locked;
    }

}
