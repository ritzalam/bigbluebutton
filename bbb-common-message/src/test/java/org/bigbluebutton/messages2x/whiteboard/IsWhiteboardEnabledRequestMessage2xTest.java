package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.whiteboard.IsWhiteboardEnabledReplyMessage2x;
import org.bigbluebutton.common.messages2x.whiteboard.IsWhiteboardEnabledRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class IsWhiteboardEnabledRequestMessage2xTest {

    @Test
    public void IsWhiteboardEnabledRequestMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        String replyTo = "req123/blah";

        IsWhiteboardEnabledRequestMessage2x msg1 = new IsWhiteboardEnabledRequestMessage2x(meetingID,
                requesterID, replyTo);

        String json1 = msg1.toJson();

        System.out.println(json1);

        IsWhiteboardEnabledRequestMessage2x msg2 = IsWhiteboardEnabledRequestMessage2x.fromJson(json1);

        Assert.assertEquals(IsWhiteboardEnabledRequestMessage2x.NAME,
                msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(replyTo, msg1.payload.replyTo);
        Assert.assertEquals(requesterID, msg1.payload.requesterID);
    }

}
