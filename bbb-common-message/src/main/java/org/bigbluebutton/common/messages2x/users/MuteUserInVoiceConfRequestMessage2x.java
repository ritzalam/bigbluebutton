package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class MuteUserInVoiceConfRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "MuteUserInVoiceConfRequestMessage";
    public final Payload payload;

    public MuteUserInVoiceConfRequestMessage2x(String meetingID, String voiceConf, String
            voiceUserID, Boolean mute) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.voiceConf = voiceConf;
        payload.voiceUserID = voiceUserID;
        payload.mute = mute;
    }

    public static MuteUserInVoiceConfRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, MuteUserInVoiceConfRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String voiceConf;
        public String voiceUserID;
        public Boolean mute;
    }

}
