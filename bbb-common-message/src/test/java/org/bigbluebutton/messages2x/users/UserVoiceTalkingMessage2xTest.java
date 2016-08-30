package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.objects.EmojiStatus;
import org.bigbluebutton.common.messages2x.objects.Role;
import org.bigbluebutton.common.messages2x.objects.User;
import org.bigbluebutton.common.messages2x.objects.VoiceUser;
import org.bigbluebutton.common.messages2x.users.UserVoiceTalkingMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class UserVoiceTalkingMessage2xTest {

    @Test
    public void UserVoiceTalkingMessage2x() {

        String meetingID = "meetingabc123";
        String voiceConf = "78789";

        Boolean talking = true;
        Boolean voiceLocked = false;
        Boolean muted = false;
        Boolean joined = false;
        String callerName = "some callername";
        String callerNum = "some callernum";
        String webUserID = "somewebuserid";
        String voiceUserID = "somevoiceuserid";

        VoiceUser vu = new VoiceUser(talking, voiceLocked, muted, joined, callerName, callerNum,
                webUserID, voiceUserID);

        String userID = "someuserid";
        String username = "some user name";
        Boolean hasStream = false;
        Boolean listenOnly = true;
        EmojiStatus emojiStatus = EmojiStatus.CONFUSED;
        Boolean phoneUser = false;
        Boolean presenter = false;
        Boolean locked = false;
        String extUserID = "exteruserid";
        Role role = Role.MODERATOR;
        String avatarURL = "some/avatar/URL";
        ArrayList<String> webcamStreams = new ArrayList<String>();
        webcamStreams.add("stream1");
        webcamStreams.add("stream2");
        webcamStreams.add("stream3");

        User user = new User(userID, username, hasStream, listenOnly, emojiStatus, phoneUser,
                presenter, locked, extUserID, role, avatarURL, vu, webcamStreams);

        UserVoiceTalkingMessage2x msg1 = new UserVoiceTalkingMessage2x(meetingID,
                user, voiceConf);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserVoiceTalkingMessage2x msg2 = UserVoiceTalkingMessage2x.fromJson(json1);

        Assert.assertEquals(UserVoiceTalkingMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(voiceConf, msg2.payload.voiceConf);

        // voice user
        Assert.assertEquals(talking, msg2.payload.user.voiceUser.talking);
        Assert.assertEquals(voiceLocked, msg2.payload.user.voiceUser.voiceLocked);
        Assert.assertEquals(muted, msg2.payload.user.voiceUser.muted);
        Assert.assertEquals(joined, msg2.payload.user.voiceUser.joined);
        Assert.assertEquals(callerName, msg2.payload.user.voiceUser.callerName);
        Assert.assertEquals(callerNum, msg2.payload.user.voiceUser.callerNum);
        Assert.assertEquals(webUserID, msg2.payload.user.voiceUser.webUserID);
        Assert.assertEquals(voiceUserID, msg2.payload.user.voiceUser.voiceUserID);

        // web user
        Assert.assertEquals(userID, msg2.payload.user.userID);
        Assert.assertEquals(username, msg2.payload.user.username);
        Assert.assertEquals(hasStream, msg2.payload.user.hasStream);
        Assert.assertEquals(listenOnly, msg2.payload.user.listenOnly);
        Assert.assertEquals(emojiStatus, msg2.payload.user.emojiStatus);
        Assert.assertEquals(phoneUser, msg2.payload.user.phoneUser);
        Assert.assertEquals(presenter, msg2.payload.user.presenter);
        Assert.assertEquals(locked, msg2.payload.user.locked);
        Assert.assertEquals(extUserID, msg2.payload.user.extUserID);
        Assert.assertEquals(role, msg2.payload.user.role);
        Assert.assertEquals(avatarURL, msg2.payload.user.avatarURL);
        Assert.assertEquals(webcamStreams, msg2.payload.user.webcamStreams);
    }

}
