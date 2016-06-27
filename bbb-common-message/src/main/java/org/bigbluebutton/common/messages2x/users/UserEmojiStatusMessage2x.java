package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.EmojiStatus;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserEmojiStatusMessage2x extends AbstractEventMessage {

    public static final String NAME = "UserEmojiStatusMessage";
    public final Payload payload;

    public UserEmojiStatusMessage2x(String meetingID, String userID, EmojiStatus emojiStatus) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.emojiStatus = emojiStatus;
    }

    public static UserEmojiStatusMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserEmojiStatusMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public EmojiStatus emojiStatus;
    }

}
