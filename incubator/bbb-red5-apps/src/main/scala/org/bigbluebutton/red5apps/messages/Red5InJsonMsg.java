package org.bigbluebutton.red5apps.messages;

public class Red5InJsonMsg {

    public String json;
    public String name;
    public String connectionId;
    public String sessionToken;

    public Red5InJsonMsg(String name, String json, String connectionId, String sessionToken) {
        this.json = json;
        this.name = name;
        this.connectionId = connectionId;
        this.sessionToken = sessionToken;
    }
}
