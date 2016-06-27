package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.StartRecordingVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class StartRecordingVoiceConfRequestMessage2xTest {

    @Test
    public void StartRecordingVoiceConfRequestMessage2x() {
        String meetingID = "meeting123";
        String voiceConference = "78789";

        StartRecordingVoiceConfRequestMessage2x msg1 = new StartRecordingVoiceConfRequestMessage2x(meetingID,
                voiceConference);

        String json1 = msg1.toJson();

        System.out.println(json1);

        StartRecordingVoiceConfRequestMessage2x msg2 = StartRecordingVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(StartRecordingVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConference, msg2.payload.voiceConference);
    }

}
