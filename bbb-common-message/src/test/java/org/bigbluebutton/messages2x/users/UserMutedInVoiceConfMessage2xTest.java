package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserMutedInVoiceConfMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserMutedInVoiceConfMessage2xTest {

    @Test
    public void UserMutedInVoiceConfMessage2x() {

        String voiceConf = "78978";
        String voiceUserID = "userabc123";
        Boolean muted = true;

        UserMutedInVoiceConfMessage2x msg1 = new UserMutedInVoiceConfMessage2x(voiceConf, voiceUserID,
                muted);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserMutedInVoiceConfMessage2x msg2 = UserMutedInVoiceConfMessage2x.fromJson(json1);

        Assert.assertEquals(UserMutedInVoiceConfMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
        Assert.assertEquals(muted, msg2.payload.muted);
    }

}
