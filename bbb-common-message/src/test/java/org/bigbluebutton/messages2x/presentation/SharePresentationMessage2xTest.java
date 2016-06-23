package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.presentation.SharePresentationMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SharePresentationMessage2xTest {

    @Test
    public void SharePresentationMessage2x() {
        String meetingID = "abc123";
        String presentationID = "pres123";
        Boolean share = true;

        SharePresentationMessage2x msg1 = new SharePresentationMessage2x(meetingID, presentationID,
                share);

        String json1 = msg1.toJson();

        System.out.println(json1);

        SharePresentationMessage2x msg2 = SharePresentationMessage2x.fromJson(json1);

        Assert.assertEquals(SharePresentationMessage2x.NAME, msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(share, msg1.payload.share);
        Assert.assertEquals(presentationID, msg1.payload.presentationID);
    }
}
