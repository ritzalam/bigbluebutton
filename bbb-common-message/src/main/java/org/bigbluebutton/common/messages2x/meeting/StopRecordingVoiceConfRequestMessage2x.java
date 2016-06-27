package org.bigbluebutton.common.messages2x.meeting;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class StopRecordingVoiceConfRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "StopRecordingVoiceConfRequestMessage";
    public final Payload payload;

    public StopRecordingVoiceConfRequestMessage2x(String meetingID, String voiceConference,
                                                  String recordStream) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.voiceConference = voiceConference;
        payload.recordStream = recordStream;
    }

    public static StopRecordingVoiceConfRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, StopRecordingVoiceConfRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String voiceConference;
        public String recordStream;
    }

}
