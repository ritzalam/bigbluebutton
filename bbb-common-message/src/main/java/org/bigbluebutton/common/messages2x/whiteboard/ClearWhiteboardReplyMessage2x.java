package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ClearWhiteboardReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "ClearWhiteboardReplyMessage";
    public final Payload payload;

    public ClearWhiteboardReplyMessage2x(String meetingID, String requesterID, String whiteboardID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.whiteboardID = whiteboardID;
    }

    public static ClearWhiteboardReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ClearWhiteboardReplyMessage2x.class);
    }

    public class Payload {
        public String whiteboardID;
        public String meetingID;
        public String requesterID;
    }
}
