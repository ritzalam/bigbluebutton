package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SharePresentationMessage2x extends AbstractEventMessage {


    public static final String NAME = "SharePresentationMessage";
    public final Payload payload;

    public SharePresentationMessage2x(String meetingID, String presentationID, Boolean share) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.presentationID = presentationID;
        payload.share = share;
    }

    public static SharePresentationMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SharePresentationMessage2x.class);
    }

    public class Payload {
        public Boolean share;
        public String meetingID;
        public String presentationID;
    }
}
