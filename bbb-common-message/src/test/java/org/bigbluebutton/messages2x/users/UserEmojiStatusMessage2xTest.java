package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.objects.EmojiStatus;
import org.bigbluebutton.common.messages2x.users.UserEmojiStatusMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class UserEmojiStatusMessage2xTest {

    @Test
    public void UserEjectedFromMeetingMessage2x() {
        String meetingID = "abc123";
        String userID = "user345";
        EmojiStatus status = EmojiStatus.APPLAUSE;

        UserEmojiStatusMessage2x msg1 = new UserEmojiStatusMessage2x(meetingID, userID, status);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        UserEmojiStatusMessage2x msg2 = UserEmojiStatusMessage2x.fromJson(json1);

        Assert.assertEquals(UserEmojiStatusMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(userID, msg2.payload.userID);
        Assert.assertEquals(status, msg2.payload.emojiStatus);
    }

}
