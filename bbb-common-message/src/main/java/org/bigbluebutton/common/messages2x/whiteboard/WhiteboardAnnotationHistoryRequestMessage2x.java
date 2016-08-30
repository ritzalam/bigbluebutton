package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class WhiteboardAnnotationHistoryRequestMessage2x extends AbstractEventMessage{

    public static final String NAME = "WhiteboardAnnotationHistoryRequestMessage";
    public final Payload payload;

    public WhiteboardAnnotationHistoryRequestMessage2x(String meetingID, String requesterID, String
            whiteboardID, String replyTo) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.whiteboardID = whiteboardID;
        payload.replyTo = replyTo;
    }

    public static WhiteboardAnnotationHistoryRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, WhiteboardAnnotationHistoryRequestMessage2x.class);
    }

    public class Payload {
        public String whiteboardID;
        public String meetingID;
        public String requesterID;
        public String replyTo;
    }
}
