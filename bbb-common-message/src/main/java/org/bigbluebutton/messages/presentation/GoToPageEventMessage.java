package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GoToPageEventMessage extends AbstractMessage {
    public static final String NAME = "GoToPageEventMessage";

    public final MessageHeader header;
    public final Body body;

    public GoToPageEventMessage(MessageHeader header,
                                GoToPageEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static GoToPageEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GoToPageEventMessage.class);
    }

    public static class Body {
        public String pageId;
        public Double xPercent;
        public Double yPercent;

        public Body(String pageId) {
            this.pageId = pageId;
        }
    }

}
