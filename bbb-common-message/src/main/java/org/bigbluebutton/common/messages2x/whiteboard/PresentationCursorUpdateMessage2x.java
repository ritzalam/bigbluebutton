package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationCursorUpdateMessage2x extends AbstractEventMessage{

    public static final String NAME = "PresentationCursorUpdateMessage";
    public final Payload payload;

    public PresentationCursorUpdateMessage2x(String meetingID, Double xPercent, Double yPercent) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.xPercent = xPercent;
        payload.yPercent = yPercent;
    }

    public static PresentationCursorUpdateMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationCursorUpdateMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public Double xPercent;
        public Double yPercent;
    }

}
