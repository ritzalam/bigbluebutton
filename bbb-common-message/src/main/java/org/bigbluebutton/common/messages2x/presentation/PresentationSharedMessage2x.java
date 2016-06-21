package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.Presentation;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationSharedMessage2x extends AbstractEventMessage {
    public static final String PRESENTATION_SHARED_MESSAGE = "PresentationSharedMessage";
    public final Payload payload;

    public PresentationSharedMessage2x(String meetingID, Presentation presentation) {
        super();
        header.name = PRESENTATION_SHARED_MESSAGE;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.presentation = presentation;
    }

    public static PresentationSharedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationSharedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public Presentation presentation;
    }
}
