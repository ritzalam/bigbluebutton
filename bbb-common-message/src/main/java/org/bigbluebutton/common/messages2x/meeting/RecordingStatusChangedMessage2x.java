package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class RecordingStatusChangedMessage2x extends AbstractEventMessage {

    public static final String NAME = "RecordingStatusChangedMessage";
    public final Payload payload;

    public RecordingStatusChangedMessage2x(String meetingID, String userID, Boolean recording) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.userID = userID;
        payload.recording = recording;
    }

    public static RecordingStatusChangedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, RecordingStatusChangedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String userID;
        public Boolean recording;
    }

}
