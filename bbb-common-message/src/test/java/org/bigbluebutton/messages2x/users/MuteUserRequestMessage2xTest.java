package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.MuteUserRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class MuteUserRequestMessage2xTest {

    @Test
    public void MuteUserRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";
        String userID = "user564";
        Boolean mute = false;

        MuteUserRequestMessage2x msg1 = new MuteUserRequestMessage2x
                (meetingID, requesterID, userID, mute);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        MuteUserRequestMessage2x msg2 = MuteUserRequestMessage2x.fromJson(json1);

        Assert.assertEquals(MuteUserRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(mute, msg2.payload.mute);
    }

}
