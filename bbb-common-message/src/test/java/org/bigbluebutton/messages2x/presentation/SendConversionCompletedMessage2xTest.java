package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.bigbluebutton.common.messages2x.presentation.SendConversionCompletedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendConversionCompletedMessage2xTest {

    @Test
    public void SendConversionCompletedMessage2x() {
        String meetingID = "someMeetingID";
        MessageKey messageKey = MessageKey.CONVERSION_COMPLETED;
        PresentationCode code = PresentationCode.CONVERT;
        String presentationID = "somePresID";
        Integer numPages = 5;
        String presName = "Random Slide Show.pdf";
        String presBaseURL = "http://lala.com/pres";


        SendConversionCompletedMessage2x msg1 = new SendConversionCompletedMessage2x(meetingID,
                messageKey, code, presentationID, numPages, presName, presBaseURL);

        String json1 = msg1.toJson();

        System.out.println(json1);

        SendConversionCompletedMessage2x msg2 = SendConversionCompletedMessage2x.fromJson(json1);

        Assert.assertEquals(SendConversionCompletedMessage2x.NAME, msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(new Integer(5), msg1.payload.numPages);
        Assert.assertEquals(presBaseURL, msg1.payload.presBaseURL);
        Assert.assertEquals(code, msg1.payload.code);
        Assert.assertEquals(messageKey, msg1.payload.messageKey);
        Assert.assertEquals(presName, msg1.payload.presName);
        Assert.assertEquals(presentationID, msg1.payload.presentationID);
    }
}
