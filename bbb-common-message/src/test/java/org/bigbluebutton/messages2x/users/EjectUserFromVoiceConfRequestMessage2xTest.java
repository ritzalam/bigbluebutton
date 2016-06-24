package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.EjectUserFromVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class EjectUserFromVoiceConfRequestMessage2xTest {

    @Test
    public void EjectUserFromVoiceConfRequestMessage2x() {
        String meetingID = "meetingABC123";
        String voiceConf = "78798";
        String voiceUserID = "user345-lala";

        EjectUserFromVoiceConfRequestMessage2x msg1 = new EjectUserFromVoiceConfRequestMessage2x
                (meetingID, voiceConf, voiceUserID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        EjectUserFromVoiceConfRequestMessage2x msg2 = EjectUserFromVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(EjectUserFromVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
    }

}
