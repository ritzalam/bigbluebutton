package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.presentation.GetPresentationInfoRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetPresentationInfoRequestMessage2xTest {

    @Test
    public void GetPresentationInfoRequestMessage2x() {

        String meetingID = "abc123";
        String requesterID = "req123";
        String replyTo = "replyTo123";

        GetPresentationInfoRequestMessage2x msg1 = new GetPresentationInfoRequestMessage2x(meetingID,
                requesterID, replyTo);

        String json1 = msg1.toJson();

        System.out.println(json1);

        GetPresentationInfoRequestMessage2x msg2 = GetPresentationInfoRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetPresentationInfoRequestMessage2x.GET_PRESENTATION_INFO_REQUEST_MESSAGE,
                msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(replyTo, msg1.payload.replyTo);
        Assert.assertEquals(requesterID, msg1.payload.requesterID);
    }
}
