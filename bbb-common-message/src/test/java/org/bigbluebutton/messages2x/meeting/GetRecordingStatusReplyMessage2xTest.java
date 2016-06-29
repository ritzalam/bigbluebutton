package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.GetRecordingStatusReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetRecordingStatusReplyMessage2xTest {

    @Test
    public void GetRecordingStatusReplyMessage2x() {
        String meetingID = "meeting123";
        String userID = "user456";
        Boolean recording = true;

        GetRecordingStatusReplyMessage2x msg1 = new
                GetRecordingStatusReplyMessage2x(meetingID, userID, recording);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetRecordingStatusReplyMessage2x msg2 =
                GetRecordingStatusReplyMessage2x.fromJson(json1);

        Assert.assertEquals(GetRecordingStatusReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(recording, msg2.payload.recording);
    }

}
