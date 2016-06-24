package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.bigbluebutton.common.messages2x.presentation.SendPageCountErrorMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendPageCountErrorMessage2xTest {

    @Test
    public void SendPageCountErrorMessage2x() {
        String meetingID = "someMeetingID";
        MessageKey messageKey = MessageKey.CONVERSION_COMPLETED;
        PresentationCode code = PresentationCode.CONVERT;
        String presentationID = "somePresID";
        String presName = "Random Slide Show.pdf";
        Integer numberOfPages = 74;
        Integer maxNumberOfPages = 70;


        SendPageCountErrorMessage2x msg1 = new SendPageCountErrorMessage2x(meetingID,
                messageKey, code, presentationID, presName, numberOfPages, maxNumberOfPages);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        SendPageCountErrorMessage2x msg2 = SendPageCountErrorMessage2x.fromJson(json1);

        Assert.assertEquals(SendPageCountErrorMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(code, msg2.payload.code);
        Assert.assertEquals(messageKey, msg2.payload.messageKey);
        Assert.assertEquals(presName, msg2.payload.presName);
        Assert.assertEquals(presentationID, msg2.payload.presentationID);
        Assert.assertEquals(numberOfPages, msg2.payload.numberOfPages);
        Assert.assertEquals(maxNumberOfPages, msg2.payload.maxNumberOfPages);
    }
}
