package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.UndoAnnotationRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UndoAnnotationRequestMessage2xTest {

    @Test
    public void UndoAnnotationRequestMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID= "white123";

        UndoAnnotationRequestMessage2x msg1 = new UndoAnnotationRequestMessage2x(meetingID,
                requesterID, whiteboardID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UndoAnnotationRequestMessage2x msg2 = UndoAnnotationRequestMessage2x.fromJson(json1);

        Assert.assertEquals(UndoAnnotationRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(whiteboardID, msg2.payload.whiteboardID);
    }
}
