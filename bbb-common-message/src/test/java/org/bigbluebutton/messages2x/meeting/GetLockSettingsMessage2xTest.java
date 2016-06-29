package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.GetLockSettingsMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetLockSettingsMessage2xTest {

    @Test
    public void GetLockSettingsMessage2x() {
        String meetingID = "meeting123";
        String userID = "user456";

        GetLockSettingsMessage2x msg1 = new
                GetLockSettingsMessage2x(meetingID, userID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetLockSettingsMessage2x msg2 =
                GetLockSettingsMessage2x.fromJson(json1);

        Assert.assertEquals(GetLockSettingsMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
    }

}
