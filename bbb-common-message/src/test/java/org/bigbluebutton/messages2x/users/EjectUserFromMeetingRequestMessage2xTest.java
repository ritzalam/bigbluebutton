package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.EjectUserFromMeetingRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class EjectUserFromMeetingRequestMessage2xTest {

    @Test
    public void EjectUserFromMeetingRequestMessage2x() {
        String meetingID = "meetingABC123";
        String userID = "aUser-id123";
        String ejectedBy = "user345";

        EjectUserFromMeetingRequestMessage2x msg1 = new EjectUserFromMeetingRequestMessage2x
                (meetingID, userID, ejectedBy);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        EjectUserFromMeetingRequestMessage2x msg2 = EjectUserFromMeetingRequestMessage2x.fromJson(json1);

        Assert.assertEquals(EjectUserFromMeetingRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(ejectedBy, msg2.payload.ejectedBy);
    }

}
