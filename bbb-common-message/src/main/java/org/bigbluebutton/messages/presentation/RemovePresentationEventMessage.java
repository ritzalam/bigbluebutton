package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class RemovePresentationEventMessage extends AbstractMessage {
    public static final String NAME = "RemovePresentationEventMessage";

    public final MessageHeader header;
    public final Body body;

    public RemovePresentationEventMessage(MessageHeader header,
                                          RemovePresentationEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static RemovePresentationEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, RemovePresentationEventMessage.class);
    }

    public static class Body {
        public String presentationId;

        public Body(String presentationId) {
            this.presentationId = presentationId;
        }
    }

}
