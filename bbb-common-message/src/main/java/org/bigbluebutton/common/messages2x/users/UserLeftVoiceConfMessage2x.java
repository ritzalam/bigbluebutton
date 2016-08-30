package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserLeftVoiceConfMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserLeftVoiceConfMessage";
    public final Payload payload;

    public UserLeftVoiceConfMessage2x(String voiceConf, String voiceUserID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.voiceConf = voiceConf;
        payload.voiceUserID = voiceUserID;
    }

    public static UserLeftVoiceConfMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserLeftVoiceConfMessage2x.class);
    }

    public class Payload {
        public String voiceConf;
        public String voiceUserID;
    }

}
