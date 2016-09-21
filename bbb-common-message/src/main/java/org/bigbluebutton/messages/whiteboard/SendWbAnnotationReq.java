package org.bigbluebutton.messages.whiteboard;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.Map;

public class SendWbAnnotationReq extends AbstractMessage {
    public static final String NAME = "SendWbAnnotationReq";

    public final MessageHeader header;
    public final SendWbAnnotationReqBody body;

    public SendWbAnnotationReq(MessageHeader header,
                               SendWbAnnotationReqBody body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SendWbAnnotationReq fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendWbAnnotationReq.class);
    }

    public static class SendWbAnnotationReqBody {
        public String whiteboardId;
        public String annotationType;
        public Map<String, Object> annotation;

        public SendWbAnnotationReqBody(String whiteboardId, String annotationType,
                                       Map<String, Object> annotation) {
            this.annotation = annotation;
            this.annotationType = annotationType;
            this.whiteboardId = whiteboardId;

        }
    }

}
