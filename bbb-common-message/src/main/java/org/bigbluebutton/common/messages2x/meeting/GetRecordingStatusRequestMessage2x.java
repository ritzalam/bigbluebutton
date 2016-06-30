package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetRecordingStatusRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetRecordingStatusRequestMessage";
    public final Payload payload;

    public GetRecordingStatusRequestMessage2x(String meetingID, String userID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
    }

    public static GetRecordingStatusRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetRecordingStatusRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
    }

}
