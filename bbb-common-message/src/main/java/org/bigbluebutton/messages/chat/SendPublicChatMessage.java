package org.bigbluebutton.messages.chat;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.vo.ChatPropertiesBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendPublicChatMessage extends AbstractMessage {
    public static final String NAME = "SendPublicChatMessage";

    public final MessageHeader header;
    public final SendPublicChatMessageBody body;

    public SendPublicChatMessage(MessageHeader header, SendPublicChatMessageBody body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SendPublicChatMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendPublicChatMessage.class);
    }

    public static class SendPublicChatMessageBody {
        public ChatPropertiesBody chatMessage;

        public SendPublicChatMessageBody(ChatPropertiesBody chatMessage) {
            this.chatMessage = chatMessage;
        }
    }
}
