package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.SetRecordingStatusRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SetRecordingStatusRequestMessage2xTest {

    @Test
    public void PresenterAssignedMessage2x() {
        String meetingID = "meeting123";
        String userID = "user123";
        Boolean recording = true;

        SetRecordingStatusRequestMessage2x msg1 = new SetRecordingStatusRequestMessage2x(meetingID,
                userID, recording);

        String json1 = msg1.toJson();

        System.out.println(json1);

        SetRecordingStatusRequestMessage2x msg2 = SetRecordingStatusRequestMessage2x.fromJson(json1);

        Assert.assertEquals(SetRecordingStatusRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(recording, msg2.payload.recording);
    }

}
