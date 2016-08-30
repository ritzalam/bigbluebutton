package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.IsMeetingMutedRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class IsMeetingMutedRequestMessage2xTest {

    @Test
    public void IsMeetingMutedRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";

        IsMeetingMutedRequestMessage2x msg1 = new IsMeetingMutedRequestMessage2x(meetingID,
                requesterID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        IsMeetingMutedRequestMessage2x msg2 = IsMeetingMutedRequestMessage2x.fromJson(json1);

        Assert.assertEquals(IsMeetingMutedRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }

}
