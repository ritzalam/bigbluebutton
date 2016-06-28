package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserStatusChangedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserStatusChangedMessage2xTest {

    @Test
    public void UserStatusChangedMessage2x() {

        String meetingID = "meetingabc123";
        String userID = "userabc123";
        String status = "user-status"; //TODO this is probably Role enum
        String value = "user-value"; //TODO check what this represents

        UserStatusChangedMessage2x msg1 = new UserStatusChangedMessage2x(meetingID,
                userID, status, value);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserStatusChangedMessage2x msg2 = UserStatusChangedMessage2x.fromJson(json1);

        Assert.assertEquals(UserStatusChangedMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(status, msg2.payload.status);
        Assert.assertEquals(value, msg2.payload.value);
    }

}
