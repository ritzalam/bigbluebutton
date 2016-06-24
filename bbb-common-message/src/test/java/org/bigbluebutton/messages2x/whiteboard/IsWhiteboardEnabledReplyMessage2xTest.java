package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.IsWhiteboardEnabledReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class IsWhiteboardEnabledReplyMessage2xTest {

    @Test
    public void IsWhiteboardEnabledReplyMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        Boolean enable = true;

        IsWhiteboardEnabledReplyMessage2x msg1 = new IsWhiteboardEnabledReplyMessage2x(meetingID,
                requesterID, enable);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        IsWhiteboardEnabledReplyMessage2x msg2 = IsWhiteboardEnabledReplyMessage2x.fromJson(json1);

        Assert.assertEquals(IsWhiteboardEnabledReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(enable, msg2.payload.enable);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }

}
