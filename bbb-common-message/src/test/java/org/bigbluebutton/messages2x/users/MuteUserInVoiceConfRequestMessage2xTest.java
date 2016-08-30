package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.MuteUserInVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class MuteUserInVoiceConfRequestMessage2xTest {

    @Test
    public void MuteUserInVoiceConfRequestMessage2x() {
        String meetingID = "meeting123";
        String voiceConf = "78789";
        String voiceUserID = "user123";
        Boolean mute = true;

        MuteUserInVoiceConfRequestMessage2x msg1 = new MuteUserInVoiceConfRequestMessage2x
                (meetingID, voiceConf, voiceUserID, mute);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        MuteUserInVoiceConfRequestMessage2x msg2 = MuteUserInVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(MuteUserInVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
        Assert.assertEquals(mute, msg2.payload.mute);
    }

}
