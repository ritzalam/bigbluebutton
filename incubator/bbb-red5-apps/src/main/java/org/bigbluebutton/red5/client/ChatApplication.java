package org.bigbluebutton.red5.client;

import org.bigbluebutton.red5.client.messaging.*;

import org.bigbluebutton.messages.chat.GetChatHistoryRequestMessage;
import org.bigbluebutton.messages.chat.SendPrivateChatMessage;
import org.bigbluebutton.messages.chat.SendPublicChatMessage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatApplication {
    private ConnectionInvokerService service;

    public ChatApplication(ConnectionInvokerService service) {
        this.service = service;
    }

    public void handleChatMessage(String message) {

        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(message);

        if (obj.has("header") && obj.has("payload")) {
            JsonObject header = (JsonObject) obj.get("header");

            if (header.has("name")) {
                String messageName = header.get("name").getAsString();
                String meetingId = header.get("meeting_id").getAsString();

                if (SendPublicChatMessage.NAME.equals(messageName)) {
                    BroadcastClientJsonMessage m = new BroadcastClientJsonMessage(meetingId,
                            "ChatReceivePublicMessageCommand", message);
                    service.sendMessage(m);

                } else if (SendPrivateChatMessage.NAME.equals(messageName)) {
                    SendPrivateChatMessage privateMessageObject = SendPrivateChatMessage.fromJson
                            (message);
                    if (privateMessageObject != null) {
                        String toUserId = privateMessageObject.body.chatMessage.toUserID;
                        DirectClientJsonMessage receiver = new DirectClientJsonMessage(meetingId,
                                toUserId, "ChatReceivePrivateMessageCommand", message);
                        service.sendMessage(receiver);

                        DirectClientJsonMessage sender = new DirectClientJsonMessage(meetingId,
                                privateMessageObject.body.requesterID,
                                "ChatReceivePrivateMessageCommand", message);
                        service.sendMessage(sender);
                    }
                } else if (GetChatHistoryRequestMessage.NAME.equals(messageName)) {
                    GetChatHistoryRequestMessage historyMessageObject = GetChatHistoryRequestMessage
                            .fromJson(message);

                    if (historyMessageObject != null) {
                        DirectClientJsonMessage m = new DirectClientJsonMessage(meetingId,
                                historyMessageObject.body.requesterID,
                                "GetChatHistoryRequestMessage", message);
                        service.sendMessage(m);
                    }
                }
            }
        }
    }

}
