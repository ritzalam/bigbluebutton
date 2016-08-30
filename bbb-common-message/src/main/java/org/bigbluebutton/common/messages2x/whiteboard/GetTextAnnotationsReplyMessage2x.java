package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.TextAnnotation;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetTextAnnotationsReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetTextAnnotationsReplyMessage";
    public final Payload payload;

    public GetTextAnnotationsReplyMessage2x(String meetingID, String requesterID,
                                        String whiteboardID, TextAnnotation[] annotations) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.whiteboardID = whiteboardID;
        payload.annotations = annotations;
    }

    public static GetTextAnnotationsReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetTextAnnotationsReplyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String whiteboardID;
        public TextAnnotation[] annotations;
    }

}
