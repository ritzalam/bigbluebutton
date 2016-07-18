package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.MuteAllRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class MuteAllRequestMessage2xTest {

    @Test
    public void MuteAllRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";
        Boolean mute = false;

        MuteAllRequestMessage2x msg1 = new MuteAllRequestMessage2x
                (meetingID, requesterID, mute);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        MuteAllRequestMessage2x msg2 = MuteAllRequestMessage2x.fromJson(json1);

        Assert.assertEquals(MuteAllRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(mute, msg2.payload.mute);
    }

}
