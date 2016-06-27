package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.DisconnectAllUsersMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class DisconnectAllUsersMessage2xTest {

    @Test
    public void DisconnectAllUsersMessage2x() {
        String meetingID = "meeting123";

        DisconnectAllUsersMessage2x msg1 = new DisconnectAllUsersMessage2x(meetingID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        DisconnectAllUsersMessage2x msg2 = DisconnectAllUsersMessage2x.fromJson(json1);

        Assert.assertEquals(DisconnectAllUsersMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
    }

}
