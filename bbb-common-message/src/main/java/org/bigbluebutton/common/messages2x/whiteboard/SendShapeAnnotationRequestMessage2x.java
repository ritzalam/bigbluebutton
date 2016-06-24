package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.ShapeAnnotation;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendShapeAnnotationRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendShapeAnnotationRequestMessage";
    public final Payload payload;

    public SendShapeAnnotationRequestMessage2x(String meetingID, String requesterID,
                                               ShapeAnnotation shape) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.shape = shape;
    }

    public static SendShapeAnnotationRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendShapeAnnotationRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public ShapeAnnotation shape;
    }

}
