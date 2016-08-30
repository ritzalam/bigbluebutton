package org.bigbluebutton.messages.body;

public class MessageHeader {
    public final String name;
    public final String meetingId;
    public final String senderId;
    public final String replyTo;

    public MessageHeader(String name, String meetingId, String senderId, String replyTo) {
        this.name = name;
        this.meetingId = meetingId;
        this.senderId = senderId;
        this.replyTo = replyTo;
    }

}
