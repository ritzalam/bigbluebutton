package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserTalkingInVoiceConfMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserTalkingInVoiceConfMessage";
    public final Payload payload;

    public UserTalkingInVoiceConfMessage2x(String voiceConf, String voiceUserID, Boolean talking) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.voiceConf = voiceConf;
        payload.voiceUserID = voiceUserID;
        payload.talking = talking;
    }

    public static UserTalkingInVoiceConfMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserTalkingInVoiceConfMessage2x.class);
    }

    public class Payload {
        public String voiceConf;
        public String voiceUserID;
        public Boolean talking;
    }

}
