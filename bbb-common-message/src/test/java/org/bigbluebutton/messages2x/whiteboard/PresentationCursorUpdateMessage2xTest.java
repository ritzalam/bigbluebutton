package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.PresentationCursorUpdateMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class PresentationCursorUpdateMessage2xTest  {

    @Test
    public void PresentationCursorUpdateMessage2x() {

        String meetingID = "abc123";
        Double xPercent = 23.4;
        Double yPercent = 0.0;

        PresentationCursorUpdateMessage2x msg1 = new PresentationCursorUpdateMessage2x(meetingID,
                xPercent, yPercent);
        String json1 = msg1.toJson();

        System.out.println(json1);

        PresentationCursorUpdateMessage2x msg2 = PresentationCursorUpdateMessage2x.fromJson(json1);

        Assert.assertEquals(PresentationCursorUpdateMessage2x.NAME,
                msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(xPercent, msg1.payload.xPercent);
        Assert.assertEquals(yPercent, msg1.payload.yPercent);
    }

}
