package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserTalkingInVoiceConfMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserTalkingInVoiceConfMessage2xTest {

    @Test
    public void UserTalkingInVoiceConfMessage2x() {

        String voiceConf = "78978";
        String voiceUserID = "userabc123";
        Boolean talking = true;

        UserTalkingInVoiceConfMessage2x msg1 = new UserTalkingInVoiceConfMessage2x(voiceConf, voiceUserID,
                talking);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserTalkingInVoiceConfMessage2x msg2 = UserTalkingInVoiceConfMessage2x.fromJson(json1);

        Assert.assertEquals(UserTalkingInVoiceConfMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
        Assert.assertEquals(talking, msg2.payload.talking);
    }

}
