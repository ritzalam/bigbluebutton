package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.TextAnnotation;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendTextAnnotationRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendTextAnnotationRequestMessage";
    public final Payload payload;

    public SendTextAnnotationRequestMessage2x(String meetingID, String requesterID,
                                               TextAnnotation shape) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.shape = shape;
    }

    public static SendTextAnnotationRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendTextAnnotationRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public TextAnnotation shape;
    }

}
