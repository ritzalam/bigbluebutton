package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserLeftVoiceConfMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserLeftVoiceConfMessage2xTest {

    @Test
    public void UserLeftVoiceConfMessage2x() {

        String voiceConf = "78789";
        String voiceUserID = "abc123";

        UserLeftVoiceConfMessage2x msg1 = new UserLeftVoiceConfMessage2x(voiceConf, voiceUserID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserLeftVoiceConfMessage2x msg2 = UserLeftVoiceConfMessage2x.fromJson(json1);

        Assert.assertEquals(UserLeftVoiceConfMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
    }

}
