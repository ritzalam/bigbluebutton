package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.PresentationPage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GoToSlideMessage2x extends AbstractEventMessage {

    public static final String NAME = "GoToSlideMessage";
    public final Payload payload;

    public GoToSlideMessage2x(String meetingID, PresentationPage slide) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.slide = slide;
    }

    public static GoToSlideMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GoToSlideMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public PresentationPage slide;
    }
}
