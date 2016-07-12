package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendCursorUpdateEventMessage extends AbstractMessage {
    public static final String NAME = "SendCursorUpdateEventMessage";

    public final MessageHeader header;
    public final Body body;

    public SendCursorUpdateEventMessage(MessageHeader header,
                                        SendCursorUpdateEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SendCursorUpdateEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendCursorUpdateEventMessage.class);
    }

    public static class Body {
        public String pageId;
        public Double xPercent;
        public Double yPercent;

        public Body(String pageId, Double xPercent, Double yPercent) {
            this.pageId = pageId;
            this.xPercent = xPercent;
            this.yPercent = yPercent;
        }
    }

}
