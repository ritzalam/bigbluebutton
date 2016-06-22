package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.ClearWhiteboardReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class ClearWhiteboardReplyMessage2xTest {

    @Test
    public void ClearWhiteboardReplyMessage2x() {

        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID = "whiteboardID123";

        ClearWhiteboardReplyMessage2x msg1 = new ClearWhiteboardReplyMessage2x(meetingID,
                requesterID, whiteboardID);

        String json1 = msg1.toJson();

        System.out.println(json1);

        ClearWhiteboardReplyMessage2x msg2 = ClearWhiteboardReplyMessage2x.fromJson(json1);

        Assert.assertEquals(ClearWhiteboardReplyMessage2x.NAME,
                msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(whiteboardID, msg1.payload.whiteboardID);
        Assert.assertEquals(requesterID, msg1.payload.requesterID);
    }

}
