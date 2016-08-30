package org.bigbluebutton.messages.body;

import org.bigbluebutton.messages.MessageType;

public class MessageHeaderToClient {
    public final String name;
    public final String meetingId;
    public final String receiverId;
    public final MessageType messageType;

    public MessageHeaderToClient(String name, String meetingId, String receiverId, MessageType messageType) {
        this.name = name;
        this.meetingId = meetingId;
        this.receiverId = receiverId;
        this.messageType = messageType;
    }
}
