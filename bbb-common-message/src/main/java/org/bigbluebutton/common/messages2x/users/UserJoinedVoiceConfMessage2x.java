package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserJoinedVoiceConfMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserJoinedVoiceConfMessage";
    public final Payload payload;

    public UserJoinedVoiceConfMessage2x(String voiceConfID, String voiceUserID, String userID,
                                        String callerIDName, String callerIDNum, Boolean muted,
                                        Boolean talking, String avatarURL) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.voiceConfID = voiceConfID;
        payload.voiceUserID = voiceUserID;
        payload.userID = userID;
        payload.callerIDName = callerIDName;
        payload.callerIDNum = callerIDNum;
        payload.muted = muted;
        payload.talking = talking;
        payload.avatarURL = avatarURL;
    }

    public static UserJoinedVoiceConfMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserJoinedVoiceConfMessage2x.class);
    }

    public class Payload {
        public String voiceConfID;
        public String voiceUserID;
        public String userID;
        public String callerIDName;
        public String callerIDNum;
        public Boolean muted;
        public Boolean talking;
        public String avatarURL;
    }

}
