package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserDisconnectedFromGlobalAudioMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserDisconnectedFromGlobalAudioMessage2xTest {

    @Test
    public void UserDisconnectedFromGlobalAudioMessage2x() {
        String userID = "aUser-id123";
        String name = "Disconnecting User";
        String voiceConf = "78789";

        UserDisconnectedFromGlobalAudioMessage2x msg1 = new UserDisconnectedFromGlobalAudioMessage2x
                (userID, name, voiceConf);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserDisconnectedFromGlobalAudioMessage2x msg2 = UserDisconnectedFromGlobalAudioMessage2x.fromJson(json1);

        Assert.assertEquals(UserDisconnectedFromGlobalAudioMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(name, msg2.payload.name);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);
    }

}
