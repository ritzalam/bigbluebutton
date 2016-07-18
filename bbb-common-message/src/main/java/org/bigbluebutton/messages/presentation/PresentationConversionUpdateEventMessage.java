package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationConversionUpdateEventMessage extends AbstractMessage {
    public static final String NAME = "PresentationConversionUpdateEventMessage";

    public final MessageHeader header;
    public final Body body;

    public PresentationConversionUpdateEventMessage(MessageHeader header,
                                                    PresentationConversionUpdateEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static PresentationConversionUpdateEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationConversionUpdateEventMessage.class);
    }

    public static class Body {
        public String messageKey;
        public String code; //TODO put these in enum
        public String presentationId;


        public Body(String presentationId, String messageKey, String code) {
            this.presentationId = presentationId;
            this.messageKey = messageKey;
            this.code = code;
        }
    }

}
