package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.DisconnectUserMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class DisconnectUserMessage2xTest {

    @Test
    public void DisconnectUserMessage2x() {
        String meetingID = "meeting123";
        String userID = "user123";

        DisconnectUserMessage2x msg1 = new DisconnectUserMessage2x(meetingID, userID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        DisconnectUserMessage2x msg2 = DisconnectUserMessage2x.fromJson(json1);

        Assert.assertEquals(DisconnectUserMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
    }

}
