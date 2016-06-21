package org.bigbluebutton.messages2x.presentation;

import org.bigbluebutton.common.messages2x.presentation.PresentationRemovedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class PresentationRemovedMessage2xTest {

    @Test
    public void PresentationRemovedMessage2x() {

        String meetingID = "183f0bf3a0982a127bdb8161e0c44eb696b3e75c-1466188833793";
        String presentationID = "4714091acbecb16d0eba28963cd7daabd64d36f2-1466190062619";

        PresentationRemovedMessage2x msg1 = new PresentationRemovedMessage2x(meetingID,
                presentationID);

        String json1 = msg1.toJson();
        System.out.println(json1);
        PresentationRemovedMessage2x msg2 = PresentationRemovedMessage2x.fromJson(json1);

        Assert.assertEquals(msg2.header.name, PresentationRemovedMessage2x.PRESENTATION_REMOVED_MESSAGE);
        Assert.assertEquals(msg2.payload.meetingID, meetingID);
        Assert.assertEquals(msg2.payload.presentationID, presentationID);
    }
}
