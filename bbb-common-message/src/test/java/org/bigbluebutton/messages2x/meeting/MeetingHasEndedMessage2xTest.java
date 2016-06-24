package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.MeetingHasEndedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class MeetingHasEndedMessage2xTest {

    @Test
    public void MeetingHasEndedMessage2x() {
        String meetingID = "meeting123";

        MeetingHasEndedMessage2x msg1 = new MeetingHasEndedMessage2x(meetingID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        MeetingHasEndedMessage2x msg2 = MeetingHasEndedMessage2x.fromJson(json1);

        Assert.assertEquals(MeetingHasEndedMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
    }

}
