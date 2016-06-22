package org.bigbluebutton.common.messages2x.chat;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.ChatMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendPrivateChatMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendPrivateChatMessage";
    public final Payload payload;

    public SendPrivateChatMessage2x(String meetingID, String requesterID, ChatMessage chatMessage) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.message = chatMessage;
    }

    public static SendPrivateChatMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendPrivateChatMessage2x.class);
    }

    public class Payload {
        public ChatMessage message;
        public String meetingID;
        public String requesterID;
    }
}
