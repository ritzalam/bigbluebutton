package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserLockedInVoiceConfMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserLockedInVoiceConfMessage2xTest {

    @Test
    public void UserLockedInVoiceConfMessage2x() {

        String voiceConf = "78978";
        String voiceUserID = "userabc123";
        Boolean locked = true;

        UserLockedInVoiceConfMessage2x msg1 = new UserLockedInVoiceConfMessage2x(voiceConf, voiceUserID,
                locked);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserLockedInVoiceConfMessage2x msg2 = UserLockedInVoiceConfMessage2x.fromJson(json1);

        Assert.assertEquals(UserLockedInVoiceConfMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
        Assert.assertEquals(locked, msg2.payload.locked);
    }

}
