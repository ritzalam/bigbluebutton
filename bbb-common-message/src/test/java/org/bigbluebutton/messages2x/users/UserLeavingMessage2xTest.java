package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserLeavingMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserLeavingMessage2xTest {

    @Test
    public void UserLeavingMessage2x() {
        String meetingID = "abc123";
        String userID = "user345";

        UserLeavingMessage2x msg1 = new UserLeavingMessage2x(meetingID, userID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserLeavingMessage2x msg2 = UserLeavingMessage2x.fromJson(json1);

        Assert.assertEquals(UserLeavingMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
    }

}
