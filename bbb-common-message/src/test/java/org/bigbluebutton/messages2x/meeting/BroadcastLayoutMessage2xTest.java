package org.bigbluebutton.messages2x.meeting;

import org.bigbluebutton.common.messages2x.meeting.BroadcastLayoutMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class BroadcastLayoutMessage2xTest {

    @Test
    public void BroadcastLayoutMessage2x() {
        String meetingID = "meeting123";
        String setByUserID = "user123";
        String layout = "some layout";
        Boolean locked = true;
        ArrayList<String> users = new ArrayList<>();
        users.add("userid002");
        users.add("userid003");
        users.add("userid004");
        users.add("userid005");

        BroadcastLayoutMessage2x msg1 = new BroadcastLayoutMessage2x(meetingID,
                setByUserID, layout, locked, users);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        BroadcastLayoutMessage2x msg2 = BroadcastLayoutMessage2x.fromJson(json1);

        Assert.assertEquals(BroadcastLayoutMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(setByUserID, msg2.payload.setByUserID);
        Assert.assertEquals(layout, msg2.payload.layout);
        Assert.assertEquals(locked, msg2.payload.locked);
        Assert.assertEquals(users, msg2.payload.users);
    }

}
