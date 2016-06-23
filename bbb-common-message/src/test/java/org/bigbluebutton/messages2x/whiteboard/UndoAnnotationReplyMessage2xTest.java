package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.UndoAnnotationReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UndoAnnotationReplyMessage2xTest {

    @Test
    public void UndoAnnotationRequestMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID= "white123";
        String shapeID= "shape123";

        UndoAnnotationReplyMessage2x msg1 = new UndoAnnotationReplyMessage2x(meetingID,
                requesterID, whiteboardID, shapeID);

        String json1 = msg1.toJson();

        System.out.println(json1);

        UndoAnnotationReplyMessage2x msg2 = UndoAnnotationReplyMessage2x.fromJson(json1);

        Assert.assertEquals(UndoAnnotationReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(whiteboardID, msg2.payload.whiteboardID);
        Assert.assertEquals(shapeID, msg2.payload.shapeID);
    }
}
