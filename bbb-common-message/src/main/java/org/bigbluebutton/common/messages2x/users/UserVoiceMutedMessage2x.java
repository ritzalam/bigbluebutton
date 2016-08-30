package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.User;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserVoiceMutedMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserVoiceMutedMessage";
    public final Payload payload;

    public UserVoiceMutedMessage2x(String meetingID, User user, String voiceConf) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.user = user;
        payload.voiceConf = voiceConf;
    }

    public static UserVoiceMutedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserVoiceMutedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public User user;
        public String voiceConf;
    }

}
