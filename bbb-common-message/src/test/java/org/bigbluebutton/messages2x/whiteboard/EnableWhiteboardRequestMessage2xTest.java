package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.EnableWhiteboardRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class EnableWhiteboardRequestMessage2xTest {

    @Test
    public void EnableWhiteboardRequestMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        Boolean enable = true;

        EnableWhiteboardRequestMessage2x msg1 = new EnableWhiteboardRequestMessage2x(meetingID,
                requesterID, enable);

        String json1 = msg1.toJson();

        System.out.println(json1);

        EnableWhiteboardRequestMessage2x msg2 = EnableWhiteboardRequestMessage2x.fromJson(json1);

        Assert.assertEquals(EnableWhiteboardRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(enable, msg2.payload.enable);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }

}
