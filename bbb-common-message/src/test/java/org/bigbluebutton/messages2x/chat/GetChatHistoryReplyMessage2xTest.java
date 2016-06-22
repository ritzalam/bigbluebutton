package org.bigbluebutton.messages2x.chat;

import org.bigbluebutton.common.messages2x.chat.GetChatHistoryReplyMessage2x;
import org.bigbluebutton.common.messages2x.objects.ChatMessage;
import org.bigbluebutton.common.messages2x.objects.ChatType;
import org.junit.Assert;
import org.junit.Test;

public class GetChatHistoryReplyMessage2xTest {

    @Test
    public void GetChatHistoryReplyMessage2x() {

        String meetingID = "abc123";
        String requesterID = "req123";

        String fromColor1 = "0";
        String fromTime1 = "1466104668970";
        ChatType chatType1 = ChatType.PUBLIC_CHAT;
        String toUserID1 = "public_chat_userid";
        String message1 = "SOME PUBLIC MESSAGE 111111111";
        String fromUsername1 = "Mr Sender";
        String fromUserID1 = "dqbdcadojnsh_2";
        String toUsername1 = "Mr Receiver";
        String fromTimezoneOffset1 = "240";

        ChatMessage chat1 = new ChatMessage(fromColor1, fromTime1, chatType1, toUserID1, message1,
                fromUsername1, fromUserID1, toUsername1, fromTimezoneOffset1);

        String fromColor2 = "5";
        String fromTime2 = "1466104668970";
        ChatType chatType2 = ChatType.PUBLIC_CHAT;
        String toUserID2 = "public_chat_userid";
        String message2 = "SOME PUBLIC MESSAGE 2222";
        String fromUsername2 = "Mrs Sender";
        String fromUserID2 = "dqbdcadojnsh_2";
        String toUsername2 = "Mrs Receiver";
        String fromTimezoneOffset2 = "240";

        ChatMessage chat2 = new ChatMessage(fromColor2, fromTime2, chatType2, toUserID2, message2,
                fromUsername2, fromUserID2, toUsername2, fromTimezoneOffset2);

        String fromColor3 = "7";
        String fromTime3 = "1466104668970";
        ChatType chatType3 = ChatType.PUBLIC_CHAT;
        String toUserID3 = "public_chat_userid";
        String message3 = "SOME PUBLIC MESSAGE 33333";
        String fromUsername3 = "Mr Sender";
        String fromUserID3 = "dqbdcadojnsh_2";
        String toUsername3 = "Mr Receiver";
        String fromTimezoneOffset3 = "240";

        ChatMessage chat3 = new ChatMessage(fromColor3, fromTime3, chatType3, toUserID3, message3,
                fromUsername3, fromUserID3, toUsername3, fromTimezoneOffset3);

        ChatMessage[] arr = new ChatMessage[] { chat1, chat2, chat3 };

        GetChatHistoryReplyMessage2x msg1 = new GetChatHistoryReplyMessage2x(meetingID,
                requesterID, arr);

        String json1 = msg1.toJson();

        System.out.println(json1);

        GetChatHistoryReplyMessage2x msg2 = GetChatHistoryReplyMessage2x.fromJson(json1);

        Assert.assertEquals(GetChatHistoryReplyMessage2x.NAME, msg1.header.name);
        Assert.assertEquals(3, msg1.payload.chatHistory.length);
        Assert.assertEquals(meetingID, msg1.payload.meetingID);
        Assert.assertEquals(message1, msg1.payload.chatHistory[0].message);
    }
}
