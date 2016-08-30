package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.GetCurrentLayoutRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetCurrentLayoutRequestMessage2xTest {

    @Test
    public void GetCurrentLayoutRequestMessage2x() {
        String meetingID = "meeting123";
        String userID = "user456";

        GetCurrentLayoutRequestMessage2x msg1 = new
                GetCurrentLayoutRequestMessage2x(meetingID, userID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetCurrentLayoutRequestMessage2x msg2 =
                GetCurrentLayoutRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetCurrentLayoutRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
    }

}
