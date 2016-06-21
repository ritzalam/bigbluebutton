package org.bigbluebutton.messages2x.presentation;

        import org.bigbluebutton.common.messages2x.objects.MessageKey;
        import org.bigbluebutton.common.messages2x.objects.PresentationCode;
        import org.bigbluebutton.common.messages2x.presentation.PresentationConversionErrorMessage2x;
        import org.junit.Assert;
        import org.junit.Test;

public class PresentationConversionErrorMessage2xTest {

    @Test
    public void PresentationConversionErrorMessage2x() {

        String meetingID = "183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793";
        String presentationID = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619";
        PresentationCode code = PresentationCode.CONVERT;
        MessageKey messageKey = MessageKey.CONVERSION_COMPLETED;
        String presentationName = "1to6.pdf";
        Integer numPages = 6;
        Integer maxNumPages = 200;

        PresentationConversionErrorMessage2x msg1 = new PresentationConversionErrorMessage2x(meetingID,
                presentationID, code, messageKey, presentationName, numPages, maxNumPages);

        String json1 = msg1.toJson();
        System.out.println(json1);
        PresentationConversionErrorMessage2x msg2 = PresentationConversionErrorMessage2x.fromJson
                (json1);

        Assert.assertEquals(PresentationConversionErrorMessage2x.PRESENTATION_CONVERSION_ERROR,
                msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(new Integer(6), msg2.payload.numPages);
    }
}
