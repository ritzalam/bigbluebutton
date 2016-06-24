package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserDisconnectedFromGlobalAudioMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserDisconnectedFromGlobalAudioMessage";
    public final Payload payload;

    public UserDisconnectedFromGlobalAudioMessage2x(String userID, String name, String voiceConf) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.userID = userID;
        payload.name = name;
        payload.voiceConf = voiceConf;
    }

    public static UserDisconnectedFromGlobalAudioMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserDisconnectedFromGlobalAudioMessage2x.class);
    }

    public class Payload {
        public String userID;
        public String name;
        public String voiceConf; //TODO should this be Integer?
    }

}
