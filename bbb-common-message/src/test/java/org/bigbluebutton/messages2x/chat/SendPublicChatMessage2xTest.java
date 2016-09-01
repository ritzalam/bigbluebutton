package org.bigbluebutton.messages2x.chat;

import org.bigbluebutton.common.messages2x.chat.SendPublicChatMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendPublicChatMessage2xTest {

    @Test
    public void SendPublicChatMessage2x() {

        String meetingID = "abc123";
        String requesterID = "dqbdcadojnsh_2";

        String fromColor = "0";
        String fromTime = "1466104668970";
        ChatType chatType = ChatType.PUBLIC_CHAT;
        String toUserID = "public_chat_userid";
        String message = "SOME MESSAGE public";
        String fromUsername = "Mr Sender";
        String fromUserID = "dqbdcadojnsh_2";
        String toUsername = "Mr Receiver";
        String fromTimezoneOffset = "240";

        ChatMessage chat1 = new ChatMessage(fromColor, fromTime, chatType, toUserID, message,
                fromUsername, fromUserID, toUsername, fromTimezoneOffset);


        SendPublicChatMessage2x msg1 = new SendPublicChatMessage2x(meetingID, requesterID, chat1);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        SendPublicChatMessage2x msg2 = SendPublicChatMessage2x.fromJson(json1);

        Assert.assertEquals(SendPublicChatMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(msg1.payload.message.fromUserID, msg2.payload.message.fromUserID);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(chatType, msg2.payload.message.chatType);
    }
}
