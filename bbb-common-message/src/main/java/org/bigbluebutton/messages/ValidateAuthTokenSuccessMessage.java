package org.bigbluebutton.messages;


import org.bigbluebutton.messages.body.MessageHeaderToClient;

import java.util.List;

public class ValidateAuthTokenSuccessMessage {
    public static final String NAME = "ValidateAuthTokenSuccessMessage";

    public final MessageHeaderToClient header;
    public final ValidateAuthTokenSuccessMessageBody body;

    public ValidateAuthTokenSuccessMessage(MessageHeaderToClient header, ValidateAuthTokenSuccessMessageBody body) {
        this.header = header;
        this.body = body;
    }


    public static class ValidateAuthTokenSuccessMessageBody {

        public final List<String> roles;

        public ValidateAuthTokenSuccessMessageBody(List<String> roles) {
            this.roles = roles;
        }
    }
}
