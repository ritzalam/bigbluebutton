package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserUnsharedWebcamMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserUnsharedWebcamMessage2xTest {

    @Test
    public void UserUnsharedWebcamMessage2x() {

        String meetingID = "meetingabc123";
        String userID = "userabc123";
        String stream = "http://somestream";

        UserUnsharedWebcamMessage2x msg1 = new UserUnsharedWebcamMessage2x(meetingID,
                userID, stream);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserUnsharedWebcamMessage2x msg2 = UserUnsharedWebcamMessage2x.fromJson(json1);

        Assert.assertEquals(UserUnsharedWebcamMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(stream, msg2.payload.stream);
    }

}
