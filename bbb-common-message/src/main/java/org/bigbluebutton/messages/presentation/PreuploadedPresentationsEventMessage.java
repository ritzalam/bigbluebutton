package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.vo.PreuploadedPresentationBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.List;

public class PreuploadedPresentationsEventMessage extends AbstractMessage {
    public static final String NAME = "PreuploadedPresentationsEventMessage";

    public final MessageHeader header;
    public final Body body;

    public PreuploadedPresentationsEventMessage(MessageHeader header,
                                                PreuploadedPresentationsEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static PreuploadedPresentationsEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PreuploadedPresentationsEventMessage.class);
    }

    public static class Body {
        public List<PreuploadedPresentationBody> presentations;
        public Body(List<PreuploadedPresentationBody> presentations) {
            this.presentations = presentations;
        }
    }

}
