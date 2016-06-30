package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.GetRecordingStatusRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetRecordingStatusRequestMessage2xTest {

    @Test
    public void GetRecordingStatusRequestMessage2x() {
        String meetingID = "meeting123";
        String userID = "user456";

        GetRecordingStatusRequestMessage2x msg1 = new
                GetRecordingStatusRequestMessage2x(meetingID, userID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetRecordingStatusRequestMessage2x msg2 =
                GetRecordingStatusRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetRecordingStatusRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
    }

}
