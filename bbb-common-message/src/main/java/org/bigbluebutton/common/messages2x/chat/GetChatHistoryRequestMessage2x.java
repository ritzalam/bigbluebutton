package org.bigbluebutton.common.messages2x.chat;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetChatHistoryRequestMessage2x extends AbstractEventMessage {

    public static final String GET_CHAT_HISTORY_REQUEST = "GetChatHistoryRequestMessage";
    public final Payload payload;

    public GetChatHistoryRequestMessage2x(String meetingID, String requesterID, String replyTo) {
        super();
        header.name = GET_CHAT_HISTORY_REQUEST;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.replyTo = replyTo;
    }

    public static GetChatHistoryRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetChatHistoryRequestMessage2x.class);
    }

    public class Payload {
        public String replyTo;
        public String meetingID;
        public String requesterID;
    }
}
