package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationRemovedMessage2x extends AbstractEventMessage {
    public static final String NAME = "PresentationRemovedMessage";
    public final Payload payload;

    public PresentationRemovedMessage2x(String meetingID, String presentationID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.presentationID = presentationID;
    }

    public static PresentationRemovedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationRemovedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String presentationID;
    }
}
