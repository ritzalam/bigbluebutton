package org.bigbluebutton.messages;

import org.bigbluebutton.messages.body.MessageHeaderToClient;
import org.bigbluebutton.messages.vo.UserInfoBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ValidateAuthTokenSuccessMessage extends AbstractMessage {
    public static final String NAME = "ValidateAuthTokenSuccessMessage";

    public final MessageHeaderToClient header;
    public final UserInfoBody body;

    public ValidateAuthTokenSuccessMessage(MessageHeaderToClient header, UserInfoBody body) {
        super();
        this.header = header;
        this.body = body;
    }

    public static ValidateAuthTokenSuccessMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ValidateAuthTokenSuccessMessage.class);
    }

}
