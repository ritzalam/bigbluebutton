package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationPageGeneratedEventMessage extends AbstractMessage {
    public static final String NAME = "PresentationPageGeneratedEventMessage";

    public final MessageHeader header;
    public final Body body;

    public PresentationPageGeneratedEventMessage(MessageHeader header,
                                                 PresentationPageGeneratedEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static PresentationPageGeneratedEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationPageGeneratedEventMessage.class);
    }

    public static class Body {
        public String messageKey;
        public String code; //TODO put these in enum
        public String presentationId;
        public Integer numberOfPages;
        public Integer pagesCompleted;

        public Body(String presentationId, String messageKey, String code, Integer numberOfPages,
         Integer pagesCompleted) {
            this.presentationId = presentationId;
            this.messageKey = messageKey;
            this.code = code;
            this.numberOfPages = numberOfPages;
            this.pagesCompleted = pagesCompleted;
        }
    }

}
