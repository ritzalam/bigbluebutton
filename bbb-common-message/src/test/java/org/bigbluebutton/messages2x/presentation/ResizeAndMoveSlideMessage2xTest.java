package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.presentation.ResizeAndMoveSlideMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class ResizeAndMoveSlideMessage2xTest {

    @Test
    public void ResizeAndMoveSlideMessage2x() {

        String meetingID = "abc123";
        Double xOffset = 1.5;
        Double yOffset = 5.5;
        Double widthRatio = 7.7;
        Double heightRatio = 10.01;

        ResizeAndMoveSlideMessage2x msg1 = new ResizeAndMoveSlideMessage2x(meetingID, xOffset,
                yOffset, widthRatio, heightRatio);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        ResizeAndMoveSlideMessage2x msg2 = ResizeAndMoveSlideMessage2x.fromJson(json1);

        Assert.assertEquals(ResizeAndMoveSlideMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(xOffset, msg2.payload.xOffset);
        Assert.assertEquals(yOffset, msg2.payload.yOffset);
        Assert.assertEquals(widthRatio, msg2.payload.widthRatio);
        Assert.assertEquals(heightRatio, msg2.payload.heightRatio);
    }
}
