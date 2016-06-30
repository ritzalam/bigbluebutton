package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserListeningOnlyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserListeningOnlyMessage2xTest {

    @Test
    public void UserListeningOnlyMessage2x() {

        String meetingID = "abc123";
        String userID = "userabc123";
        Boolean listenOnly = true;

        UserListeningOnlyMessage2x msg1 = new UserListeningOnlyMessage2x(meetingID, userID,
                listenOnly);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserListeningOnlyMessage2x msg2 = UserListeningOnlyMessage2x.fromJson(json1);

        Assert.assertEquals(UserListeningOnlyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(listenOnly, msg2.payload.listenOnly);
    }

}
