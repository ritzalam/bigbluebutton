package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class RemovePresentationMessage2x extends AbstractEventMessage {

    public static final String NAME = "RemovePresentationMessage";



    public final Payload payload;

    public RemovePresentationMessage2x(String meetingID, String presentationID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.presentationID = presentationID;
    }

    public static RemovePresentationMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, RemovePresentationMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String presentationID;
    }
}
