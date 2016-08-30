package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class IsWhiteboardEnabledRequestMessage2x extends AbstractEventMessage {
    public static final String NAME = "IsWhiteboardEnabledRequestMessage";
    public final Payload payload;

    public IsWhiteboardEnabledRequestMessage2x(String meetingID, String requesterID, String
            replyTo) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.replyTo = replyTo;
    }

    public static IsWhiteboardEnabledRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, IsWhiteboardEnabledRequestMessage2x.class);
    }

    public class Payload {
        public String replyTo;
        public String meetingID;
        public String requesterID;
    }
}
