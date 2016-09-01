package org.bigbluebutton.messages;


import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class AssignUserSessionTokenMessage extends AbstractMessage {
    public static final String NAME = "AssignUserSessionTokenMessage";

    public final MessageHeader header;
    public final AssignUserSessionTokenMessageBody body;

    public AssignUserSessionTokenMessage(MessageHeader header, AssignUserSessionTokenMessageBody body) {
        this.header = header;
        this.body = body;
    }

    public static AssignUserSessionTokenMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, AssignUserSessionTokenMessage.class);
    }


    public static class  AssignUserSessionTokenMessageBody {

        public final String meetingId;
        public final String userId;
        public final String sessionToken;

        public AssignUserSessionTokenMessageBody(String meetingId, String userId, String sessionToken) {
            this.meetingId = meetingId;
            this.userId = userId;
            this.sessionToken = sessionToken;
        }
    }


}
