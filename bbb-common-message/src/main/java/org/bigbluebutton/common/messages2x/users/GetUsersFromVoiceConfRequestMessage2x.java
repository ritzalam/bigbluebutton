package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetUsersFromVoiceConfRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetUsersFromVoiceConfRequestMessage";
    public final Payload payload;

    public GetUsersFromVoiceConfRequestMessage2x(String meetingID, String voiceConf) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.voiceConf = voiceConf;
    }

    public static GetUsersFromVoiceConfRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetUsersFromVoiceConfRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String voiceConf;
    }

}
