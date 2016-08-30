package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.LockLayoutMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class LockLayoutMessage2xTest {

    @Test
    public void LockLayoutMessage2x() {
        String meetingID = "meeting123";
        String setByUserID = "user123";
        Boolean locked = false;
        ArrayList<String> users = new ArrayList<>();

        users.add("userid01");
        users.add("userid02");
        users.add("userid03");

        LockLayoutMessage2x msg1 = new LockLayoutMessage2x(meetingID, setByUserID, locked, users);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        LockLayoutMessage2x msg2 = LockLayoutMessage2x.fromJson(json1);

        Assert.assertEquals(LockLayoutMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(setByUserID, msg2.payload.setByUserID);
        Assert.assertEquals(locked, msg2.payload.locked);
        Assert.assertEquals(users, msg2.payload.users);
    }

}
