package org.bigbluebutton.messages.body;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class MessageHeader {
    public final String name;
    public final String meetingId;
    public final String senderId;

    public MessageHeader(String name, String meetingId, String senderId) {
        this.name = name;
        this.meetingId = meetingId;
        this.senderId = senderId;
    }

}
