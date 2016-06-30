package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.User;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserJoinedVoiceMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserJoinedVoiceMessage";
    public final Payload payload;

    public UserJoinedVoiceMessage2x(String meetingID, String voiceConf, User user) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.voiceConf = voiceConf;
        payload.user = user;
    }

    public static UserJoinedVoiceMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserJoinedVoiceMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String voiceConf;
        public User user;
    }

}
