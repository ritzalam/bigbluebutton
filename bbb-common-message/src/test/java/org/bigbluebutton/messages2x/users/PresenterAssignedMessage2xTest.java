package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.PresenterAssignedMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class PresenterAssignedMessage2xTest {

    @Test
    public void PresenterAssignedMessage2x() {
        String meetingID = "meeting123";
        String newPresenterUserID = "user123";
        String newPresenterName = "Mrs Presenter";
        String assignedBy = "user456";

        PresenterAssignedMessage2x msg1 = new PresenterAssignedMessage2x(meetingID,
                newPresenterUserID, newPresenterName, assignedBy);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        PresenterAssignedMessage2x msg2 = PresenterAssignedMessage2x.fromJson(json1);

        Assert.assertEquals(PresenterAssignedMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(newPresenterUserID, msg2.payload.newPresenterUserID);
        Assert.assertEquals(newPresenterName, msg2.payload.newPresenterName);
        Assert.assertEquals(assignedBy, msg2.payload.assignedBy);
    }

}
