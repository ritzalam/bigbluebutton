package org.bigbluebutton.messages2x;

import org.bigbluebutton.common.messages2x.DestroyMeetingMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class DestroyMeetingMessage2xTest {

    @Test
    public void DestroyMeetingMessage2x() {
        String meetingID = "meeting123";

        DestroyMeetingMessage2x msg1 = new DestroyMeetingMessage2x(meetingID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        DestroyMeetingMessage2x msg2 = DestroyMeetingMessage2x.fromJson(json1);

        Assert.assertEquals(DestroyMeetingMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
    }

}
