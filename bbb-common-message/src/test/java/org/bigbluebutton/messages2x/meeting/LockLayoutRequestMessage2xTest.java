package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.LockLayoutRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class LockLayoutRequestMessage2xTest {

    @Test
    public void LockLayoutRequestMessage2x() {
        String meetingID = "meeting123";
        String userID = "user123";
        Boolean lock = false;
        Boolean viewersOnly = false;

        LockLayoutRequestMessage2x msg1 = new LockLayoutRequestMessage2x(meetingID, userID,
                lock, viewersOnly);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        LockLayoutRequestMessage2x msg2 = LockLayoutRequestMessage2x.fromJson(json1);

        Assert.assertEquals(LockLayoutRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(lock, msg2.payload.lock);
        Assert.assertEquals(viewersOnly, msg2.payload.viewersOnly);
    }

}
