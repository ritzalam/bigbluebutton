package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.ClearWhiteboardRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class ClearWhiteboardRequestMessage2xTest {

    @Test
    public void ClearWhiteboardRequestMessage2x() {

        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID = "whiteboardID123";

        ClearWhiteboardRequestMessage2x msg1 = new ClearWhiteboardRequestMessage2x(meetingID,
                requesterID, whiteboardID);

        String json1 = msg1.toJson();

        System.out.println(json1);

        ClearWhiteboardRequestMessage2x msg2 = ClearWhiteboardRequestMessage2x.fromJson(json1);

        Assert.assertEquals(ClearWhiteboardRequestMessage2x.NAME,
                msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(whiteboardID, msg1.payload.whiteboardID);
        Assert.assertEquals(requesterID, msg1.payload.requesterID);
    }

}
