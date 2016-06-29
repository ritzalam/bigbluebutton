package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.LockUserMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class LockUserMessage2xTest {

    @Test
    public void LockUserMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user456";
        String internalUserID = "user123";
        Boolean lock = false;

        LockUserMessage2x msg1 = new LockUserMessage2x(meetingID,
                requesterID, internalUserID, lock);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        LockUserMessage2x msg2 = LockUserMessage2x.fromJson(json1);

        Assert.assertEquals(LockUserMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(internalUserID, msg2.payload.internalUserID);
        Assert.assertEquals(lock, msg2.payload.lock);
    }

}
