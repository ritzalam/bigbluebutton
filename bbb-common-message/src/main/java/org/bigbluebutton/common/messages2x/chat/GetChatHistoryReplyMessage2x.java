package org.bigbluebutton.common.messages2x.chat;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.ChatMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetChatHistoryReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetChatHistoryReplyMessage";
    public final Payload payload;

    public GetChatHistoryReplyMessage2x(String meetingID, String requesterID, ChatMessage[]
            chatHistory) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.chatHistory = chatHistory;
    }

    public static GetChatHistoryReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetChatHistoryReplyMessage2x.class);
    }

    public class Payload {
        public ChatMessage[] chatHistory;
        public String meetingID;
        public String requesterID;
    }
}
