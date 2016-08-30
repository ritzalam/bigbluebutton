package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.IsMeetingMutedReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class IsMeetingMutedReplyMessage2xTest {

    @Test
    public void IsMeetingMutedReplyMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";
        Boolean muted = true;

        IsMeetingMutedReplyMessage2x msg1 = new IsMeetingMutedReplyMessage2x(meetingID,
                requesterID, muted);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        IsMeetingMutedReplyMessage2x msg2 = IsMeetingMutedReplyMessage2x.fromJson(json1);

        Assert.assertEquals(IsMeetingMutedReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(muted, msg2.payload.muted);
    }

}
