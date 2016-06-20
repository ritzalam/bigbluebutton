package org.bigbluebutton.messages2x.chat;

import org.bigbluebutton.common.messages2x.chat.SendPrivateChatMessage2x;
import org.bigbluebutton.common.messages2x.objects.ChatMessage;
import org.bigbluebutton.common.messages2x.objects.ChatType;
import org.junit.Assert;
import org.junit.Test;

public class SendPrivateChatMessage2xTest {

    @Test
    public void SendPrivateChatMessage2x() {

        String meetingID = "abc123";
        String requesterID = "dqbdcadojnsh_2";

        String fromColor = "0";
        String fromTime = "1466104668970";
        ChatType chatType = ChatType.PRIVATE_CHAT;
        String toUserID = "dqbdcadojnsh_2";
        String toUsername = "Mr Receiver";
        String message = "SOME PRIVATE MESSAGE for " + toUsername;
        String fromUsername = "Mr Sender";
        String fromUserID = "dqbdcadojnsh_2";
        String fromTimezoneOffset = "240";

        ChatMessage chat1 = new ChatMessage(fromColor, fromTime, chatType, toUserID, message,
                fromUsername, fromUserID, toUsername, fromTimezoneOffset);

        SendPrivateChatMessage2x msg1 = new SendPrivateChatMessage2x(meetingID, requesterID, chat1);

        String json1 = msg1.toJson();

        System.out.println(json1);

        SendPrivateChatMessage2x msg2 = SendPrivateChatMessage2x.fromJson(json1);

        Assert.assertEquals(msg1.header.name, SendPrivateChatMessage2x.SEND_PRIVATE_CHAT_MESSAGE);
        Assert.assertEquals(msg1.payload.message.fromUserID, msg2.payload.message.fromUserID);
        Assert.assertEquals(msg1.payload.meetingID, meetingID);
        Assert.assertEquals(msg1.payload.message.chatType, ChatType.PRIVATE_CHAT);
    }
}
