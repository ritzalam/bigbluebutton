package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.ShapeAnnotation;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetShapeAnnotationsReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetShapeAnnotationsReplyMessage";
    public final Payload payload;

    public GetShapeAnnotationsReplyMessage2x(String meetingID, String requesterID,
                                             String whiteboardID, ShapeAnnotation[] annotations) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.whiteboardID = whiteboardID;
        payload.annotations = annotations;
    }

    public static GetShapeAnnotationsReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetShapeAnnotationsReplyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String whiteboardID;
        public ShapeAnnotation[] annotations;
    }

}
