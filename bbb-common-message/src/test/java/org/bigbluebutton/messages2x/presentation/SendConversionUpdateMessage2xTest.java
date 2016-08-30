package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.bigbluebutton.common.messages2x.presentation.SendConversionUpdateMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendConversionUpdateMessage2xTest {

    @Test
    public void SendConversionUpdateMessage2x() {
        String meetingID = "someMeetingID";
        MessageKey messageKey = MessageKey.CONVERSION_COMPLETED;
        PresentationCode code = PresentationCode.CONVERT;
        String presentationID = "somePresID";
        String presName = "Random Slide Show.pdf";


        SendConversionUpdateMessage2x msg1 = new SendConversionUpdateMessage2x(meetingID,
                messageKey, code, presentationID, presName);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        SendConversionUpdateMessage2x msg2 = SendConversionUpdateMessage2x.fromJson(json1);

        Assert.assertEquals(SendConversionUpdateMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(code, msg2.payload.code);
        Assert.assertEquals(messageKey, msg2.payload.messageKey);
        Assert.assertEquals(presName, msg2.payload.presName);
        Assert.assertEquals(presentationID, msg2.payload.presentationID);
    }
}
