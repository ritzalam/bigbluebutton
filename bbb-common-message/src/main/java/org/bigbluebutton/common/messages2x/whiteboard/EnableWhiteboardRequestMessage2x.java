package org.bigbluebutton.common.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class EnableWhiteboardRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "EnableWhiteboardRequestMessage2x";
    public final Payload payload;

    public EnableWhiteboardRequestMessage2x(String meetingID, String requesterID, Boolean enable) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.enable = enable;
    }

    public static EnableWhiteboardRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, EnableWhiteboardRequestMessage2x.class);
    }

    public class Payload {
        public Boolean enable;
        public String meetingID;
        public String requesterID;
    }

}
