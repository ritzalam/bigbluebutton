package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationPageCountErrorEventMessage extends AbstractMessage {
    public static final String NAME = "PresentationPageCountErrorEventMessage";

    public final MessageHeader header;
    public final Body body;

    public PresentationPageCountErrorEventMessage(MessageHeader header,
                                                  PresentationPageCountErrorEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static PresentationPageCountErrorEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationPageCountErrorEventMessage.class);
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
