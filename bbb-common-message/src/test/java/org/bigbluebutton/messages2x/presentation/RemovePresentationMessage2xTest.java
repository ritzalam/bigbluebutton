package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.presentation.RemovePresentationMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class RemovePresentationMessage2xTest {

    @Test
    public void RemovePresentationMessage2x() {
        String meetingID = "abc123";
        String presentationID = "somePresID";

        RemovePresentationMessage2x msg1 = new RemovePresentationMessage2x
                (meetingID, presentationID);
        String json1 = msg1.toJson();
        System.out.println(json1);
        RemovePresentationMessage2x msg2 = RemovePresentationMessage2x.fromJson(json1);

        Assert.assertEquals(RemovePresentationMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(presentationID, msg2.payload.presentationID);
    }

}
