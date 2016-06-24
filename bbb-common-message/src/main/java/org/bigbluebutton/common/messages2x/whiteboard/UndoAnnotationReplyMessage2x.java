package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;


public class UndoAnnotationReplyMessage2x extends AbstractEventMessage{

    public static final String NAME = "UndoAnnotationReplyMessage";
    public final Payload payload;

    public UndoAnnotationReplyMessage2x(String meetingID, String requesterID, String
            whiteboardID, String shapeID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.whiteboardID = whiteboardID;
        payload.shapeID = shapeID;
    }

    public static UndoAnnotationReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, UndoAnnotationReplyMessage2x.class);
    }

    public class Payload {
        public String whiteboardID;
        public String meetingID;
        public String requesterID;
        public String shapeID;
    }
}
