package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.MeetingEndedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class MeetingEndedMessage2xTest {

    @Test
    public void MeetingEndedMessage2x() {
        String meetingID = "meeting123";

        MeetingEndedMessage2x msg1 = new MeetingEndedMessage2x(meetingID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        MeetingEndedMessage2x msg2 = MeetingEndedMessage2x.fromJson(json1);

        Assert.assertEquals(MeetingEndedMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
    }

}
