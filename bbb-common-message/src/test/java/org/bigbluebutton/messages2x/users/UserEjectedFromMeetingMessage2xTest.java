package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserEjectedFromMeetingMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserEjectedFromMeetingMessage2xTest {

    @Test
    public void UserEjectedFromMeetingMessage2x() {
        String meetingID = "abc123";
        String userID = "user345";
        String ejectedBy = "user678";

        UserEjectedFromMeetingMessage2x msg1 = new UserEjectedFromMeetingMessage2x(meetingID,
                userID, ejectedBy);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserEjectedFromMeetingMessage2x msg2 = UserEjectedFromMeetingMessage2x.fromJson(json1);

        Assert.assertEquals(UserEjectedFromMeetingMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(ejectedBy, msg2.payload.ejectedBy);
    }

}
