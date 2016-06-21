package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.Presentation;
import org.bigbluebutton.common.messages2x.objects.PresentationPage;
import org.bigbluebutton.common.messages2x.presentation.PresentationSharedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class PresentationSharedMessage2xTest {

    @Test
    public void PresentationSharedMessage2x() {
        String meetingID = "183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793";

        int widthRatio = 100;
        int heightRatio = 100;
        String txtURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/textfiles/6";
        int num = 6;
        int yOffset = 0;
        String swfURI ="http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/slide/6";
        String thumbURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/thumbnail/6";
        int xOffset = 0;
        Boolean current = false;
        String svgURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/svg/6";
        String id = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/6";

        PresentationPage page1 = new PresentationPage(widthRatio, heightRatio, txtURI, num,
                yOffset, swfURI, thumbURI, xOffset, current, svgURI, id);

        int widthRatio2 = 100;
        int heightRatio2 = 100;
        String txtURI2 = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/textfiles/5";
        int num2 = 5;
        int yOffset2 = 0;
        String swfURI2 ="http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/slide/5";
        String thumbURI2 = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/thumbnail/5";
        int xOffset2 = 0;
        Boolean current2 = true;
        String svgURI2 = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/svg/5";
        String id2 = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/5";

        PresentationPage page2 = new PresentationPage(widthRatio2, heightRatio2, txtURI2, num2,
                yOffset2, swfURI2, thumbURI2, xOffset2, current2, svgURI2, id2);

        PresentationPage[] arr = new PresentationPage[] { page1, page2 };
        String name = "1to6.pdf";
        String presId = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619";

        Presentation presentation = new Presentation(true, arr, name, presId);

        PresentationSharedMessage2x msg1 = new PresentationSharedMessage2x(meetingID, presentation);

        String json1 = msg1.toJson();
        System.out.println(json1);
        PresentationSharedMessage2x msg2 = PresentationSharedMessage2x.fromJson(json1);

        Assert.assertEquals(msg2.header.name, PresentationSharedMessage2x.PRESENTATION_SHARED_MESSAGE);
        Assert.assertEquals(msg2.payload.meetingID, meetingID);
        Assert.assertEquals(msg2.payload.presentation.pages.length, 2);
        Assert.assertEquals(msg2.payload.presentation.pages[0].xOffset, 0);
    }
}
