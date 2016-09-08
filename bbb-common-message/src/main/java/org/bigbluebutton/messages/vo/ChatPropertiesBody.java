package org.bigbluebutton.messages.vo;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ChatPropertiesBody {
    public String fromColor;
    public String fromTime;
    public String chatType;
    public String toUserID;
    public String message;
    public String fromUsername;
    public String fromUserID;
    public String toUsername;
    public String fromTimezoneOffset;

    public ChatPropertiesBody(String fromColor, String fromTime, String chatType, String toUserID,
                              String message, String fromUsername, String fromUserID, String toUsername,
                              String fromTimezoneOffset) {
        this.fromColor = fromColor;
        this.fromTime = fromTime;
        this.chatType = chatType;
        this.toUserID= toUserID;
        this.message = message;
        this.fromUsername= fromUsername;
        this.fromUserID= fromUserID;
        this.toUsername = toUsername;
        this.fromTimezoneOffset= fromTimezoneOffset;
    }

    public static ChatPropertiesBody fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ChatPropertiesBody.class);
    }
}
