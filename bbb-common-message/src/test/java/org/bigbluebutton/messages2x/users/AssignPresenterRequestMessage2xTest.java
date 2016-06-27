package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.AssignPresenterRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class AssignPresenterRequestMessage2xTest {

    @Test
    public void AssignPresenterRequestMessage2x() {
        String meetingID = "meeting123";
        String newPresenterUserID = "user123";
        String newPresenterName = "Mrs Presenter";
        String assignedBy = "user456";

        AssignPresenterRequestMessage2x msg1 = new AssignPresenterRequestMessage2x(meetingID,
                newPresenterUserID, newPresenterName, assignedBy);

        String json1 = msg1.toJson();

        System.out.println(json1);

        AssignPresenterRequestMessage2x msg2 = AssignPresenterRequestMessage2x.fromJson(json1);

        Assert.assertEquals(AssignPresenterRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(newPresenterUserID, msg2.payload.newPresenterUserID);
        Assert.assertEquals(newPresenterName, msg2.payload.newPresenterName);
        Assert.assertEquals(assignedBy, msg2.payload.assignedBy);
    }

}
