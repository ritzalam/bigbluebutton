package org.bigbluebutton.messages.body;

public class MessageHeader {
    public final String name;
    public final String dest;
    public final String src;
    public final String replyTo;

    public MessageHeader(String name, String dest, String src, String replyTo) {
        this.name = name;
        this.dest = dest;
        this.src = src;
        this.replyTo = replyTo;
    }

}
