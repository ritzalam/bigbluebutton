package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.LockMuteUserRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class LockMuteUserRequestMessage2xTest {

    @Test
    public void LockMuteUserRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user456";
        String userID = "user123";
        Boolean lock = false;

        LockMuteUserRequestMessage2x msg1 = new LockMuteUserRequestMessage2x(meetingID,
                requesterID, userID, lock);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        LockMuteUserRequestMessage2x msg2 = LockMuteUserRequestMessage2x.fromJson(json1);

        Assert.assertEquals(LockMuteUserRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(lock, msg2.payload.lock);
    }

}
