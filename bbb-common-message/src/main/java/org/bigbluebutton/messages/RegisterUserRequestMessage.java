package org.bigbluebutton.messages;


import org.bigbluebutton.messages.vo.UserInfoBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class RegisterUserRequestMessage {
    public static final String NAME = "RegisterUserRequestMessage";

    public final RegisterUserRequestMessageHeader header;
    public final UserInfoBody body;

    public RegisterUserRequestMessage(RegisterUserRequestMessageHeader header, UserInfoBody body) {
        this.header = header;
        this.body = body;
    }

    public static class RegisterUserRequestMessageHeader {
        public final String name;
        public final String meetingId;

        public RegisterUserRequestMessageHeader(String meetingId) {
            this.name = NAME;
            this.meetingId = meetingId;
        }
    }

    public String toJson() {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.writeValueAsString(this);
    }

    public static RegisterUserRequestMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, RegisterUserRequestMessage.class);
    }
}
