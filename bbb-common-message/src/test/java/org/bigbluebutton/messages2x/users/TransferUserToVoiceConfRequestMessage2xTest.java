package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.TransferUserToVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class TransferUserToVoiceConfRequestMessage2xTest {

    @Test
    public void TransferUserToVoiceConfRequestMessage2x() {
        String voiceConf = "78790";
        String targetVoiceConf = "78791";
        String voiceUserID = "user123";

        TransferUserToVoiceConfRequestMessage2x msg1 = new
                TransferUserToVoiceConfRequestMessage2x(voiceConf, targetVoiceConf, voiceUserID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        TransferUserToVoiceConfRequestMessage2x msg2 = TransferUserToVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(TransferUserToVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
        Assert.assertEquals(targetVoiceConf, msg2.payload.targetVoiceConf);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
    }

}
