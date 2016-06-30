package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.GetUsersRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetUsersRequestMessage2xTest {

    @Test
    public void GetUsersRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user456";

        GetUsersRequestMessage2x msg1 = new
                GetUsersRequestMessage2x(meetingID, requesterID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetUsersRequestMessage2x msg2 =
                GetUsersRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetUsersRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }

}
