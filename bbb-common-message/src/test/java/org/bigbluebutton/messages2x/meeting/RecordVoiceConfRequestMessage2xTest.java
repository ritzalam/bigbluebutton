package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.RecordVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class RecordVoiceConfRequestMessage2xTest {

    @Test
    public void PresenterAssignedMessage2x() {
        String meetingID = "meeting123";
        String voiceConference = "78789";
        Boolean recording = true;

        RecordVoiceConfRequestMessage2x msg1 = new RecordVoiceConfRequestMessage2x(meetingID,
                voiceConference, recording);

        String json1 = msg1.toJson();

        System.out.println(json1);

        RecordVoiceConfRequestMessage2x msg2 = RecordVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(RecordVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConference, msg2.payload.voiceConference);
        Assert.assertEquals(recording, msg2.payload.recording);
    }

}
