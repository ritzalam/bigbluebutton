package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserUnshareWebcamRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserUnshareWebcamRequestMessage2xTest {

    @Test
    public void UserUnshareWebcamRequestMessage2x() {

        String meetingID = "meetingabc123";
        String userID = "userabc123";
        String stream = "http://somestream";

        UserUnshareWebcamRequestMessage2x msg1 = new UserUnshareWebcamRequestMessage2x(meetingID,
                userID, stream);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserUnshareWebcamRequestMessage2x msg2 = UserUnshareWebcamRequestMessage2x.fromJson(json1);

        Assert.assertEquals(UserUnshareWebcamRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(stream, msg2.payload.stream);
    }

}
