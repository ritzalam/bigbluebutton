package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserConnectedToGlobalAudioMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserConnectedToGlobalAudioMessage2xTest {

    @Test
    public void UserConnectedToGlobalAudioMessage2x() {
        String userID = "aUser-id123";
        String name = "First User";
        String voiceConf = "78789";

        UserConnectedToGlobalAudioMessage2x msg1 = new UserConnectedToGlobalAudioMessage2x
                (userID, name, voiceConf);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserConnectedToGlobalAudioMessage2x msg2 = UserConnectedToGlobalAudioMessage2x.fromJson(json1);

        Assert.assertEquals(UserConnectedToGlobalAudioMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(name, msg2.payload.name);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
    }

}
