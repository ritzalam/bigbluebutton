package org.bigbluebutton.messages.chat;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.vo.ChatMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;

public class GetChatHistoryReplyMessage extends AbstractMessage {
    public static final String NAME = "GetChatHistoryReplyMessage";

    public final MessageHeader header;
    public final Body body;

    public GetChatHistoryReplyMessage(MessageHeader header, GetChatHistoryReplyMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static GetChatHistoryReplyMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetChatHistoryReplyMessage.class);
    }

    public static class Body {
        public ArrayList<ChatMessage> chatHistory;
        public String requesterID;

        public Body(String requesterID, ArrayList<ChatMessage> chatHistory) {
            this.requesterID = requesterID;
            this.chatHistory = chatHistory;
        }
    }
}
