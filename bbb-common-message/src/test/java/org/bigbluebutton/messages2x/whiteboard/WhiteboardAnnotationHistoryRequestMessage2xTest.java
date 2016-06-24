package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.WhiteboardAnnotationHistoryRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class WhiteboardAnnotationHistoryRequestMessage2xTest {

    @Test
    public void WhiteboardAnnotationHistoryRequestMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID = "white123";
        String replyTo = "req123/replyto";

        WhiteboardAnnotationHistoryRequestMessage2x msg1 = new WhiteboardAnnotationHistoryRequestMessage2x(meetingID,
                requesterID, whiteboardID, replyTo);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        WhiteboardAnnotationHistoryRequestMessage2x msg2 = WhiteboardAnnotationHistoryRequestMessage2x.fromJson(json1);

        Assert.assertEquals(WhiteboardAnnotationHistoryRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(whiteboardID, msg2.payload.whiteboardID);
        Assert.assertEquals(replyTo, msg2.payload.replyTo);
    }
}
