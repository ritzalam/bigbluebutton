package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.SendStunTurnInfoRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendStunTurnInfoRequestMessage2xTest {

    @Test
    public void SendStunTurnInfoRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";

        SendStunTurnInfoRequestMessage2x msg1 = new SendStunTurnInfoRequestMessage2x(meetingID,
                requesterID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        SendStunTurnInfoRequestMessage2x msg2 = SendStunTurnInfoRequestMessage2x.fromJson(json1);

        Assert.assertEquals(SendStunTurnInfoRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }

}
