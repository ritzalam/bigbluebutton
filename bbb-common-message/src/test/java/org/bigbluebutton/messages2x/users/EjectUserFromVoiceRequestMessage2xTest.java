package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.EjectUserFromVoiceRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class EjectUserFromVoiceRequestMessage2xTest {

    @Test
    public void EjectUserFromVoiceRequestMessage2x() {
        String meetingID = "meetingABC123";
        String userID = "user123";
        String requesterID = "user345";

        EjectUserFromVoiceRequestMessage2x msg1 = new EjectUserFromVoiceRequestMessage2x
                (meetingID, userID, requesterID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        EjectUserFromVoiceRequestMessage2x msg2 = EjectUserFromVoiceRequestMessage2x.fromJson(json1);

        Assert.assertEquals(EjectUserFromVoiceRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }

}
