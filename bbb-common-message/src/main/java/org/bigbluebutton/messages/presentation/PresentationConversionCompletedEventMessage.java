package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.vo.PresentationBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationConversionCompletedEventMessage extends AbstractMessage {
    public static final String NAME = "PresentationConversionCompletedEventMessage";

    public final MessageHeader header;
    public final Body body;

    public PresentationConversionCompletedEventMessage(MessageHeader header,
                                                       PresentationConversionCompletedEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static PresentationConversionCompletedEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationConversionCompletedEventMessage.class);
    }

    public static class Body {
        public String messageKey;
        public String code;
        public PresentationBody presentation;

        public Body(String messageKey, String code,
                    PresentationBody presentation) {
            this.messageKey = messageKey;
            this.code = code;
            this.presentation = presentation;
        }
    }

}
