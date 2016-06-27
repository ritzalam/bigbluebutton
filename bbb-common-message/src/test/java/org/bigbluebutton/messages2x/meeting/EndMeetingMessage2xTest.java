package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.EndMeetingMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class EndMeetingMessage2xTest {

    @Test
    public void EndMeetingMessage2x() {
        String meetingID = "meeting123";

        EndMeetingMessage2x msg1 = new EndMeetingMessage2x(meetingID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        EndMeetingMessage2x msg2 = EndMeetingMessage2x.fromJson(json1);

        Assert.assertEquals(EndMeetingMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
    }

}
