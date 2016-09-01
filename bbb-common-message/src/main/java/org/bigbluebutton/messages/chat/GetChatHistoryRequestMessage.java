package org.bigbluebutton.messages.chat;

import org.bigbluebutton.messages.AbstractMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.bigbluebutton.messages.body.MessageHeader;

public class GetChatHistoryRequestMessage extends AbstractMessage {
    public static final String NAME = "GetChatHistoryRequestMessage";

    public final MessageHeader header;
    public final Body body;

    public GetChatHistoryRequestMessage(MessageHeader header, GetChatHistoryRequestMessage.Body
            body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static GetChatHistoryRequestMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetChatHistoryRequestMessage.class);
    }

    public static class Body {
        public String replyTo;
        public String requesterID;

        public Body(String replyTo, String requesterID) {
            this.replyTo = replyTo;
            this.requesterID = requesterID;
        }
    }
}
