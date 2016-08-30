package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.SetUserStatusRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SetUserStatusRequestMessage2xTest {

    @Test
    public void SetUserStatusRequestMessage2x() {
        String meetingID = "meeting123";
        String userID = "user123";
        String status = "presenter"; //TODO
        String value = "true"; //TODO should this be Boolean

        SetUserStatusRequestMessage2x msg1 = new SetUserStatusRequestMessage2x(meetingID, userID,
                status, value);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        SetUserStatusRequestMessage2x msg2 = SetUserStatusRequestMessage2x.fromJson(json1);

        Assert.assertEquals(SetUserStatusRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(status, msg2.payload.status);
        Assert.assertEquals(value, msg2.payload.value);
    }

}
