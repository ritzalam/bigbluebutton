package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class RecordVoiceConfRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "RecordVoiceConfRequestMessage";
    public final Payload payload;

    public RecordVoiceConfRequestMessage2x(String meetingID, String voiceConference, Boolean recording) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.voiceConference = voiceConference;
        payload.recording = recording;
    }

    public static RecordVoiceConfRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, RecordVoiceConfRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String voiceConference;
        public Boolean recording;
    }

}
