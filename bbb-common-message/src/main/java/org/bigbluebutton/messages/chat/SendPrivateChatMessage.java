package org.bigbluebutton.messages.chat;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.vo.ChatMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendPrivateChatMessage extends AbstractMessage {
    public static final String NAME = "SendPrivateChatMessage";

    public final MessageHeader header;
    public final Body body;

    public SendPrivateChatMessage(MessageHeader header, SendPrivateChatMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SendPrivateChatMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendPrivateChatMessage.class);
    }

    public static class Body {
        public String requesterID;
        public ChatMessage chatMessage;

        public Body(String requesterID, ChatMessage chatMessage) {
            this.requesterID = requesterID;
            this.chatMessage = chatMessage;
        }
    }
}
