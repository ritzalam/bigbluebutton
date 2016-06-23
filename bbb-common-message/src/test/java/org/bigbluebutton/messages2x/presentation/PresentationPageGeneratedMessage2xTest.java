package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.bigbluebutton.common.messages2x.presentation.PresentationPageGeneratedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class PresentationPageGeneratedMessage2xTest {

    @Test
    public void PresentationPageGeneratedMessage2x() {
        String meetingID = "abc123";
        String presentationID = "somePresID";
        Integer numPages = 4;
        PresentationCode code = PresentationCode.CONVERT;
        MessageKey messageKey = MessageKey.CONVERSION_COMPLETED;
        String presentationName = "test.pdf";
        Integer pagesCompleted = 4;

        PresentationPageGeneratedMessage2x msg1 = new PresentationPageGeneratedMessage2x
                (meetingID, presentationID, numPages, code, messageKey, presentationName,
                        pagesCompleted);
        String json1 = msg1.toJson();
        System.out.println(json1);
        PresentationPageGeneratedMessage2x msg2 = PresentationPageGeneratedMessage2x.fromJson(json1);

        Assert.assertEquals(PresentationPageGeneratedMessage2x.NAME,
                msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(new Integer(4), msg2.payload.pagesCompleted);
        Assert.assertEquals(new Integer(4), msg2.payload.numPages);
        Assert.assertEquals(code, msg2.payload.code);
    }

}
