package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.PresentationPage;
import org.bigbluebutton.common.messages2x.presentation.PresentationPageResizedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class PresentationPageResizedMessage2xTest {

    @Test
    public void PresentationPageResizedMessage2x() {

         int widthRatio = 100;
         int heightRatio = 100;
         String txtURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/textfiles/6";
         int num = 6;
         int yOffset = 0;
         String swfURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/slide/6";
         String thumbURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/thumbnail/6";
         int xOffset = 0;
         Boolean current = false;
         String svgURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/svg/6";
         String id = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/6";

         PresentationPage page1 = new PresentationPage(widthRatio, heightRatio, txtURI, num,
                 yOffset, swfURI, thumbURI, xOffset, current, svgURI, id);

        String meetingID = "183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793";

        PresentationPageResizedMessage2x msg1 = new PresentationPageResizedMessage2x(meetingID,
                page1);

        String json1 = msg1.toJson();
        System.out.println(json1);
        PresentationPageResizedMessage2x msg2 = PresentationPageResizedMessage2x.fromJson(json1);
        System.out.println("___" + msg2.payload.page.xOffset);

        Assert.assertEquals(PresentationPageResizedMessage2x.PRESENTATION_PAGE_RESIZED_MESSAGE, msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(100, msg1.payload.page.widthRatio);
        Assert.assertEquals(0, msg1.payload.page.xOffset);
        Assert.assertEquals(0, msg1.payload.page.yOffset);
     }
}
