package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.users.UserJoinedVoiceConfMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserJoinedVoiceConfMessage2xTest {

    @Test
    public void UserJoinedVoiceConfMessage2x() {

        String voiceConfID = "somevoiceconfid";
        String voiceUserID = "somevoiceuserid";
        String userID = "userabc123";
        String callerIDName = "FirstUser1";
        String callerIDNum = "134";
        Boolean muted = false;
        Boolean talking = true;
        String avatarURL = "http://example.com/some/avata/url";

        UserJoinedVoiceConfMessage2x msg1 = new UserJoinedVoiceConfMessage2x(voiceConfID,
                voiceUserID, userID, callerIDName, callerIDNum, muted, talking, avatarURL);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserJoinedVoiceConfMessage2x msg2 = UserJoinedVoiceConfMessage2x.fromJson(json1);

        Assert.assertEquals(UserJoinedVoiceConfMessage2x.NAME, msg2.header.name);

        // voice user
        Assert.assertEquals(voiceConfID, msg2.payload.voiceConfID);
        Assert.assertEquals(voiceUserID, msg2.payload.voiceUserID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(callerIDName, msg2.payload.callerIDName);
        Assert.assertEquals(callerIDNum, msg2.payload.callerIDNum);
        Assert.assertEquals(muted, msg2.payload.muted);
        Assert.assertEquals(talking, msg2.payload.talking);
        Assert.assertEquals(avatarURL, msg2.payload.avatarURL);
    }

}
