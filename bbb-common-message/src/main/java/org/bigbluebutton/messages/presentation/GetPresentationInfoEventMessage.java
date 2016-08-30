package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetPresentationInfoEventMessage extends AbstractMessage {
    public static final String NAME = "GetPresentationInfoEventMessage";

    public final MessageHeader header;
    public final Body body;

    public GetPresentationInfoEventMessage(MessageHeader header,
                                           GetPresentationInfoEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static GetPresentationInfoEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetPresentationInfoEventMessage.class);
    }

    public static class Body {
        public String presentationId;

        public Body(String presentationId) {
            this.presentationId = presentationId;
        }
    }

}
