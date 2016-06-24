package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class EjectUserFromVoiceConfRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "EjectUserFromVoiceConfRequestMessage";
    public final Payload payload;

    public EjectUserFromVoiceConfRequestMessage2x(String meetingID,
                                                  String voiceConf, String voiceUserID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.voiceConf = voiceConf;
        payload.voiceUserID = voiceUserID;
    }

    public static EjectUserFromVoiceConfRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, EjectUserFromVoiceConfRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String voiceConf;
        public String voiceUserID;
    }

}
