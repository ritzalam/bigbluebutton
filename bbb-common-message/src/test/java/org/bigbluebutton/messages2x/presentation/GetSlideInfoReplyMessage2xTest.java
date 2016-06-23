package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.PresentationPage;
import org.bigbluebutton.common.messages2x.presentation.GetSlideInfoReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetSlideInfoReplyMessage2xTest {

    @Test
    public void GetSlideInfoReplyMessage2x () {
        String meetingID = "183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793";
        String requesterID = "user123";

        Integer widthRatio = 100;
        Integer heightRatio = 100;
        String txtURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/textfiles/6";
        Integer num = 6;
        Integer yOffset = 0;
        String swfURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/slide/6";
        String thumbURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/thumbnail/6";
        Integer xOffset = 0;
        Boolean current = false;
        String svgURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/svg/6";
        String id = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/6";

        PresentationPage slide = new PresentationPage(widthRatio, heightRatio, txtURI, num,
                yOffset, swfURI, thumbURI, xOffset, current, svgURI, id);

        GetSlideInfoReplyMessage2x msg1 = new GetSlideInfoReplyMessage2x(meetingID, requesterID,
                slide);

        String json1 = msg1.toJson();

        System.out.println(json1);

        GetSlideInfoReplyMessage2x msg2 = GetSlideInfoReplyMessage2x.fromJson(json1);

        Assert.assertEquals(GetSlideInfoReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(yOffset, msg2.payload.slide.yOffset);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }
}
