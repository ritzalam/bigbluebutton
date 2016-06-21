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

        Integer widthRatio = 100;
        Integer heightRatio = 100;
        String txtURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/textfiles/6";
        Integer num = 6;
        Integer yOffset = 0;
        String swfURI ="http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/slide/6";
        String thumbURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/thumbnail/6";
        Integer xOffset = 0;
        Boolean current = false;
        String svgURI = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/svg/6";
        String id = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/6";

        PresentationPage page1 = new PresentationPage(widthRatio, heightRatio, txtURI, num,
                yOffset, swfURI, thumbURI, xOffset, current, svgURI, id);

        Integer widthRatio2 = 100;
        Integer heightRatio2 = 100;
        String txtURI2 = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/textfiles/5";
        Integer num2 = 5;
        Integer yOffset2 = 0;
        String swfURI2 ="http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/slide/5";
        String thumbURI2 = "http://192.168.23.55/bigbluebutton/presentation/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793/4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619/thumbnail/5";
        Integer xOffset2 = 0;
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

        Assert.assertEquals(PresentationSharedMessage2x.PRESENTATION_SHARED_MESSAGE,
                msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(2, msg2.payload.presentation.pages.length);
        Assert.assertEquals(new Integer(0), msg2.payload.presentation.pages[0].xOffset);
    }
}
