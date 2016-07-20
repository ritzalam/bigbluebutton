package org.bigbluebutton.messages.whiteboard;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.vo.AnnotationBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendWhiteboardAnnotationRequestEventMessage extends AbstractMessage {
    public static final String NAME = "SendWhiteboardAnnotationRequestEventMessage";

    public final MessageHeader header;
    public final Body body;

    public SendWhiteboardAnnotationRequestEventMessage(MessageHeader header,
        SendWhiteboardAnnotationRequestEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SendWhiteboardAnnotationRequestEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendWhiteboardAnnotationRequestEventMessage.class);
    }

    public static class Body {
        public String requesterId;
        public AnnotationBody annotation;

        public Body(String requesterId, AnnotationBody annotation) {
            this.requesterId = requesterId;
            this.annotation = annotation;
        }
    }

}
