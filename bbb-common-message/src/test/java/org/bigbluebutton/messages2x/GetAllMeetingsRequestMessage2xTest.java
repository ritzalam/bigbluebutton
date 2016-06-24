package org.bigbluebutton.messages2x;

import org.bigbluebutton.common.messages2x.GetAllMeetingsRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetAllMeetingsRequestMessage2xTest {

    @Test
    public void GetAllMeetingsRequestMessage2x() {
        String meetingID = "meeting123";

        GetAllMeetingsRequestMessage2x msg1 = new GetAllMeetingsRequestMessage2x(meetingID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetAllMeetingsRequestMessage2x msg2 = GetAllMeetingsRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetAllMeetingsRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
    }

}
