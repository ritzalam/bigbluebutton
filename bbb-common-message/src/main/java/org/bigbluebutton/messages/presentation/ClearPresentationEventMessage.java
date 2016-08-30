package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ClearPresentationEventMessage extends AbstractMessage {
    public static final String NAME = "ClearPresentationEventMessage";

    public final MessageHeader header;
    public final Body body;

    public ClearPresentationEventMessage(MessageHeader header,
                                         ClearPresentationEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static ClearPresentationEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ClearPresentationEventMessage.class);
    }

    public static class Body {
        public String presentationId;

        public Body(String presentationId) {
            this.presentationId = presentationId;
        }
    }

}
