package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.bigbluebutton.common.messages2x.presentation.PresentationConversionProgressMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class PresentationConversionProgressMessage2xTest {

    @Test
    public void PresentationConversionProgressMessage2x() {

        String meetingID = "183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793";
        String presentationID = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619";
        PresentationCode code = PresentationCode.CONVERT;
        MessageKey messageKey = MessageKey.GENERATING_THUMBNAIL;
        String presentationName = "1to6.pdf";

        PresentationConversionProgressMessage2x msg1 = new PresentationConversionProgressMessage2x(meetingID,
                presentationID, code, messageKey, presentationName);

        String json1 = msg1.toJson();
        // System.out.println(json1);
        PresentationConversionProgressMessage2x msg2 = PresentationConversionProgressMessage2x.fromJson(json1);

        Assert.assertEquals(PresentationConversionProgressMessage2x.NAME,
                msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(presentationID, msg2.payload.presentationID);
    }
}
