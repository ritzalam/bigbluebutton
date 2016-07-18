package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetPageInfoEventMessage extends AbstractMessage {
    public static final String NAME = "GetPageInfoEventMessage";

    public final MessageHeader header;
    public final Body body;

    public GetPageInfoEventMessage(MessageHeader header,
                                   GetPageInfoEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static GetPageInfoEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetPageInfoEventMessage.class);
    }

    public static class Body {
        public String pageId;

        public Body(String pageId) {
            this.pageId = pageId;
        }
    }

}
