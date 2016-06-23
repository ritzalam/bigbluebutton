package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.presentation.GetSlideInfoRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetSlideInfoRequestMessage2xTest {

    @Test
    public void GetSlideInfoRequestMessage2x() {
        String meetingID = "183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793";
        String requesterID = "user123";
        String replyTo = "user432";

        GetSlideInfoRequestMessage2x msg1 = new GetSlideInfoRequestMessage2x(meetingID, requesterID,
                replyTo);

        String json1 = msg1.toJson();

        System.out.println(json1);

        GetSlideInfoRequestMessage2x msg2 = GetSlideInfoRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetSlideInfoRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(replyTo, msg2.payload.replyTo);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }
}
