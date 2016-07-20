package org.bigbluebutton.messages;

import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class UserJoinMeetingMessage extends AbstractMessage {
    public static final String NAME = "UserJoinMeetingMessage";

    public final MessageHeader header;
    public final Body body;

    public UserJoinMeetingMessage(MessageHeader header, UserJoinMeetingMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static UserJoinMeetingMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UserJoinMeetingMessage.class);
    }

    public static class Body {
        public String userID;
        public String token;
        public String sessionID;
        public String presenceID;
        public String userAgent;

        public Body(String userID, String token, String sessionID, String presenceID, String
                userAgent) {
            this.userID = userID;
            this.token = token;
            this.sessionID = sessionID;
            this.presenceID = presenceID;
            this.userAgent = userAgent;
        }
    }

}
