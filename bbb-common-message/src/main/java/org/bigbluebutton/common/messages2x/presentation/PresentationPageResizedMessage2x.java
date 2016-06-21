package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.PresentationPage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import org.boon.json.annotations.JsonInclude;

public class PresentationPageResizedMessage2x extends AbstractEventMessage{

    public static final String PRESENTATION_PAGE_RESIZED_MESSAGE = "PresentationPageResizedMessage";
    public final Payload payload;

    public PresentationPageResizedMessage2x(String meetingID, PresentationPage page) {
        super();
        header.name = PRESENTATION_PAGE_RESIZED_MESSAGE;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.page = page;
    }

    public static PresentationPageResizedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationPageResizedMessage2x.class);
    }

    public class Payload {
        public String meetingID;

        @JsonInclude
        public PresentationPage page;
    }
}
