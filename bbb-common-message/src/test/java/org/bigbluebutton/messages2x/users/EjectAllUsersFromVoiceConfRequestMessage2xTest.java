package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.EjectAllUsersFromVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class EjectAllUsersFromVoiceConfRequestMessage2xTest {

    @Test
    public void EjectAllUsersFromVoiceConfRequestMessage2x() {
        String meetingID = "meeting123";
        String voiceConf = "78789";

        EjectAllUsersFromVoiceConfRequestMessage2x msg1 = new
                EjectAllUsersFromVoiceConfRequestMessage2x(meetingID, voiceConf);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        EjectAllUsersFromVoiceConfRequestMessage2x msg2 =
                EjectAllUsersFromVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(EjectAllUsersFromVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
    }

}
