package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SharePresentationEventMessage extends AbstractMessage {
    public static final String NAME = "SharePresentationEventMessage";

    public final MessageHeader header;
    public final Body body;

    public SharePresentationEventMessage(MessageHeader header,
                                         SharePresentationEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SharePresentationEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SharePresentationEventMessage.class);
    }

    public static class Body {
        public String presentationId;
        public Boolean share;

        public Body(String presentationId, Boolean share) {
            this.presentationId = presentationId;
            this.share = share;
        }
    }

}
