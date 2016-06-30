package org.bigbluebutton.messages2x.poll;

import org.bigbluebutton.common.messages2x.poll.CreatePollRequestMessage2x;
import org.bigbluebutton.common.messages2x.objects.PollType;
import org.junit.Assert;
import org.junit.Test;

public class CreatePollRequestMessage2xTest {

    @Test
    public void CreatePollRequestMessage2x() {
        String meetingID = "meeting123";
        String requesterID = "user123";
        String pollID = "poll002";
        PollType pollType = PollType.TYPE1;

        CreatePollRequestMessage2x msg1 = new CreatePollRequestMessage2x(meetingID, requesterID,
                pollID, pollType);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        CreatePollRequestMessage2x msg2 = CreatePollRequestMessage2x.fromJson(json1);

        Assert.assertEquals(CreatePollRequestMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(pollID, msg2.payload.pollID);
        Assert.assertEquals(pollType, msg2.payload.pollType);
    }

}
