package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.MuteVoiceUserRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class MuteVoiceUserRequestMessage2xTest {

    @Test
    public void MuteVoiceUserRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";
        String userID = "user564";
        Boolean mute = false;

        MuteVoiceUserRequestMessage2x msg1 = new MuteVoiceUserRequestMessage2x
                (meetingID, requesterID, userID, mute);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        MuteVoiceUserRequestMessage2x msg2 = MuteVoiceUserRequestMessage2x.fromJson(json1);

        Assert.assertEquals(MuteVoiceUserRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(mute, msg2.payload.mute);
    }

}
