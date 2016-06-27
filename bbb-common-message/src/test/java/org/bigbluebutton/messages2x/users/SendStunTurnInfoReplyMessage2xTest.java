package org.bigbluebutton.messages2x.users;

import org.bigbluebutton.common.messages2x.objects.StunEntry;
import org.bigbluebutton.common.messages2x.objects.TurnEntry;
import org.bigbluebutton.common.messages2x.users.SendStunTurnInfoReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class SendStunTurnInfoReplyMessage2xTest {

    @Test
    public void SendStunTurnInfoRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";

        ArrayList<StunEntry> stuns = new ArrayList<>();
        stuns.add(new StunEntry("stun0.example.org"));
        stuns.add(new StunEntry("stun1.example.org"));
        stuns.add(new StunEntry("stun2.example.org"));
        stuns.add(new StunEntry("stun3.example.org"));

        ArrayList<TurnEntry> turns = new ArrayList<>();
        turns.add(new TurnEntry("User Name 1", "weak*password", 1, "turn0.example.org"));
        turns.add(new TurnEntry("User Name 2", "weak*password", 0, "turn1.example.org"));
        turns.add(new TurnEntry("User Name 3", "weak*password", 2, "turn2.example.org"));

        SendStunTurnInfoReplyMessage2x msg1 = new SendStunTurnInfoReplyMessage2x(meetingID,
                requesterID, stuns, turns);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        SendStunTurnInfoReplyMessage2x msg2 = SendStunTurnInfoReplyMessage2x.fromJson(json1);

        Assert.assertEquals(SendStunTurnInfoReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(4, msg2.payload.stuns.size());
        Assert.assertEquals("stun0.example.org", msg2.payload.stuns.get(0).url);
        Assert.assertEquals(3, msg2.payload.turns.size());
        Assert.assertEquals("weak*password", msg2.payload.turns.get(1).password);
        Assert.assertEquals("User Name 1", msg2.payload.turns.get(0).username);
        Assert.assertEquals(new Integer(1), msg2.payload.turns.get(0).ttl);
        Assert.assertEquals("turn2.example.org", msg2.payload.turns.get(2).url);
    }

}
