package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.TextAnnotation;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendTextAnnotationReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendTextAnnotationReplyMessage";
    public final Payload payload;

    public SendTextAnnotationReplyMessage2x(String meetingID, String requesterID,
                                              String whiteboardID, TextAnnotation shape) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.whiteboardID = whiteboardID;
        payload.shape = shape;
    }

    public static SendTextAnnotationReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendTextAnnotationReplyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String whiteboardID;
        public TextAnnotation shape;
    }

}
