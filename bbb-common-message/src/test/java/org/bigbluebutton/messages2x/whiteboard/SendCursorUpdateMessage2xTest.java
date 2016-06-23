package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.SendCursorUpdateMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendCursorUpdateMessage2xTest {

    @Test
    public void SendCursorUpdateMessage2x() {

        String meetingID = "abc123";
        Double xPercent = 23.4;
        Double yPercent = 0.0;

        SendCursorUpdateMessage2x msg1 = new SendCursorUpdateMessage2x(meetingID, xPercent,
                yPercent);
        String json1 = msg1.toJson();

        System.out.println(json1);

        SendCursorUpdateMessage2x msg2 = SendCursorUpdateMessage2x.fromJson(json1);

        Assert.assertEquals(SendCursorUpdateMessage2x.NAME, msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(xPercent, msg1.payload.xPercent);
        Assert.assertEquals(yPercent, msg1.payload.yPercent);
    }

}
