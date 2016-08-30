package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.MeetingMutedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class MeetingMutedMessage2xTest {

    @Test
    public void MeetingMutedMessage2x() {
        String meetingID = "meeting123";
        Boolean muted = false;

        MeetingMutedMessage2x msg1 = new MeetingMutedMessage2x(meetingID, muted);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        MeetingMutedMessage2x msg2 = MeetingMutedMessage2x.fromJson(json1);

        Assert.assertEquals(MeetingMutedMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(muted, msg2.payload.muted);
    }

}
