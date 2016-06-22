package org.bigbluebutton.messages2x.chat;

import org.bigbluebutton.common.messages2x.chat.GetChatHistoryRequestMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetChatHistoryRequestMessage2xTest {

    @Test
    public void GetChatHistoryRequestMessage2x() {

        String meetingID = "abc123";
        String requesterID = "req123";
        String replyTo = "replyTo123";

        GetChatHistoryRequestMessage2x msg1 = new GetChatHistoryRequestMessage2x(meetingID,
                requesterID, replyTo);

        String json1 = msg1.toJson();

        System.out.println(json1);

        GetChatHistoryRequestMessage2x msg2 = GetChatHistoryRequestMessage2x.fromJson(json1);

        Assert.assertEquals(GetChatHistoryRequestMessage2x.GET_CHAT_HISTORY_REQUEST, msg1.header.name);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(replyTo, msg1.payload.replyTo);
        Assert.assertEquals(requesterID, msg1.payload.requesterID);
    }
}
