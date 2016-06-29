package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.GetUsersFromVoiceConfRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetUsersFromVoiceConfRequestMessage2xTest {

    @Test
    public void GetUsersFromVoiceConfRequestMessage2x() {
        String meetingID = "meeting123";
        String voiceConf = "78798";

        GetUsersFromVoiceConfRequestMessage2x msg1 = new
                GetUsersFromVoiceConfRequestMessage2x(meetingID, voiceConf);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetUsersFromVoiceConfRequestMessage2x msg2 =
                GetUsersFromVoiceConfRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetUsersFromVoiceConfRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
    }

}
