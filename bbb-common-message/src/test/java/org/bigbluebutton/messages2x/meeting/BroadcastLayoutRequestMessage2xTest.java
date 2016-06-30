package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.BroadcastLayoutRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class BroadcastLayoutRequestMessage2xTest {

    @Test
    public void BroadcastLayoutRequestMessage2x() {
        String meetingID = "meeting123";
        String userID = "user123";
        String layout = "some layout";

        BroadcastLayoutRequestMessage2x msg1 = new BroadcastLayoutRequestMessage2x(meetingID,
                userID, layout);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        BroadcastLayoutRequestMessage2x msg2 = BroadcastLayoutRequestMessage2x.fromJson(json1);

        Assert.assertEquals(BroadcastLayoutRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(layout, msg2.payload.layout);
    }

}
