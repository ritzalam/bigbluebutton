package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.GetCurrentLayoutReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetCurrentLayoutReplyMessage2xTest {

    @Test
    public void GetCurrentLayoutReplyMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";
        String setByUserID = "user345";
        String layout = "some layout";
        Boolean locked = true;

        GetCurrentLayoutReplyMessage2x msg1 = new GetCurrentLayoutReplyMessage2x(meetingID,
                requesterID, setByUserID, layout, locked);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetCurrentLayoutReplyMessage2x msg2 = GetCurrentLayoutReplyMessage2x.fromJson(json1);

        Assert.assertEquals(GetCurrentLayoutReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(setByUserID, msg2.payload.setByUserID);
        Assert.assertEquals(layout, msg2.payload.layout);
        Assert.assertEquals(locked, msg2.payload.locked);
    }

}
