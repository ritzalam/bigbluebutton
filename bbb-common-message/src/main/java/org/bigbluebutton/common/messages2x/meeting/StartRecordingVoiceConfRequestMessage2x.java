package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class StartRecordingVoiceConfRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "StartRecordingVoiceConfRequestMessage";
    public final Payload payload;

    public StartRecordingVoiceConfRequestMessage2x(String meetingID, String voiceConference) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.voiceConference = voiceConference;
    }

    public static StartRecordingVoiceConfRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, StartRecordingVoiceConfRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String voiceConference;
    }

}
