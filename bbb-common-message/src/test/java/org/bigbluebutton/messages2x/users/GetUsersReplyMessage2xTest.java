package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.objects.EmojiStatus;
import org.bigbluebutton.common.messages2x.objects.Role;
import org.bigbluebutton.common.messages2x.objects.User;
import org.bigbluebutton.common.messages2x.objects.VoiceUser;
import org.bigbluebutton.common.messages2x.users.GetUsersReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class GetUsersReplyMessage2xTest {

    @Test
    public void GetUsersReplyMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user456";
        ArrayList<User> users = new ArrayList<>();

        Boolean talking = false;
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
        
        users.add(user);
        users.add(user);

        GetUsersReplyMessage2x msg1 = new
                GetUsersReplyMessage2x(meetingID, requesterID, users);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetUsersReplyMessage2x msg2 =
                GetUsersReplyMessage2x.fromJson(json1);

        Assert.assertEquals(GetUsersReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);

        Assert.assertEquals(2, msg2.payload.users.size());
        // voice user
        Assert.assertEquals(talking, msg2.payload.users.get(0).voiceUser.talking);
        Assert.assertEquals(voiceLocked, msg2.payload.users.get(0).voiceUser.voiceLocked);
        Assert.assertEquals(muted, msg2.payload.users.get(0).voiceUser.muted);
        Assert.assertEquals(joined, msg2.payload.users.get(0).voiceUser.joined);
        Assert.assertEquals(callerName, msg2.payload.users.get(0).voiceUser.callerName);
        Assert.assertEquals(callerNum, msg2.payload.users.get(0).voiceUser.callerNum);
        Assert.assertEquals(webUserID, msg2.payload.users.get(0).voiceUser.webUserID);
        Assert.assertEquals(voiceUserID, msg2.payload.users.get(0).voiceUser.voiceUserID);

        // web user
        Assert.assertEquals(userID, msg2.payload.users.get(0).userID);
        Assert.assertEquals(username, msg2.payload.users.get(0).username);
        Assert.assertEquals(hasStream, msg2.payload.users.get(0).hasStream);
        Assert.assertEquals(listenOnly, msg2.payload.users.get(0).listenOnly);
        Assert.assertEquals(emojiStatus, msg2.payload.users.get(0).emojiStatus);
        Assert.assertEquals(phoneUser, msg2.payload.users.get(0).phoneUser);
        Assert.assertEquals(presenter, msg2.payload.users.get(0).presenter);
        Assert.assertEquals(locked, msg2.payload.users.get(0).locked);
        Assert.assertEquals(extUserID, msg2.payload.users.get(0).extUserID);
        Assert.assertEquals(role, msg2.payload.users.get(0).role);
        Assert.assertEquals(avatarURL, msg2.payload.users.get(0).avatarURL);
        Assert.assertEquals(webcamStreams, msg2.payload.users.get(0).webcamStreams);
    }

}
