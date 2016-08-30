package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserLockedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserLockedMessage2xTest {

    @Test
    public void UserLockedMessage2x() {

        String meetingID = "meetingabc123";
        String userID = "userabc123";
        Boolean locked = true;

        UserLockedMessage2x msg1 = new UserLockedMessage2x(meetingID, userID, locked);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserLockedMessage2x msg2 = UserLockedMessage2x.fromJson(json1);

        Assert.assertEquals(UserLockedMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(locked, msg2.payload.locked);
    }

}
