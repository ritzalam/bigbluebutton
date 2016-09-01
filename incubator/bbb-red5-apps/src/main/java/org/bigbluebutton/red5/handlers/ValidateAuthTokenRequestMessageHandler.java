package org.bigbluebutton.red5.handlers;


import org.bigbluebutton.common.messages.MessagingConstants;
import org.bigbluebutton.messages.ValidateAuthTokenRequestMessage;
import org.bigbluebutton.red5.client.messaging.ConnectionInvokerService;
import org.bigbluebutton.red5.client.messaging.DisconnectClientMessage;
import org.bigbluebutton.red5.pubsub.redis.MessageSender;

public class ValidateAuthTokenRequestMessageHandler implements IMessageFromClientHandler {
    private MessageSender sender;
    private ConnectionInvokerService service;

    public ValidateAuthTokenRequestMessageHandler(MessageSender sender, ConnectionInvokerService service) {
        this.sender = sender;
        this.service = service;
    }

    public void handle(String meetingId, String senderId, String json) {
        ValidateAuthTokenRequestMessage msg = ValidateAuthTokenRequestMessage.fromJson(json);
        if (msg.header.meetingId.equals(meetingId) && msg.header.senderId.equals(senderId)) {
            sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
        } else {
            DisconnectClientMessage discMsg = new DisconnectClientMessage(msg.header.meetingId, msg.header.senderId);
            service.sendMessage(discMsg);
        }
    }
}
