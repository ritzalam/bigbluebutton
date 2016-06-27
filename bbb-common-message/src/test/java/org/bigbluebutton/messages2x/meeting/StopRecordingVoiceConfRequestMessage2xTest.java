package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.StopRecordingVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class StopRecordingVoiceConfRequestMessage2xTest {

    @Test
    public void StopRecordingVoiceConfRequestMessage2x() {
        String meetingID = "meeting123";
        String voiceConference = "78789";
        String recordStream = "http://example.com/id/somestream/stream";

        StopRecordingVoiceConfRequestMessage2x msg1 = new StopRecordingVoiceConfRequestMessage2x(meetingID,
                voiceConference, recordStream);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        StopRecordingVoiceConfRequestMessage2x msg2 = StopRecordingVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(StopRecordingVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConference, msg2.payload.voiceConference);
        Assert.assertEquals(recordStream, msg2.payload.recordStream);
    }

}
