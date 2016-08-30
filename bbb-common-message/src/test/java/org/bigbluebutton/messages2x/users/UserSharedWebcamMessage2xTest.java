package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserSharedWebcamMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserSharedWebcamMessage2xTest {

    @Test
    public void UserSharedWebcamMessage2x() {

        String meetingID = "meetingabc123";
        String userID = "userabc123";
        String stream = "http://somestream";

        UserSharedWebcamMessage2x msg1 = new UserSharedWebcamMessage2x(meetingID,
                userID, stream);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserSharedWebcamMessage2x msg2 = UserSharedWebcamMessage2x.fromJson(json1);

        Assert.assertEquals(UserSharedWebcamMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(stream, msg2.payload.stream);
    }

}
