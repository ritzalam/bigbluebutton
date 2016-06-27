package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class TurnEntry {

    public final String username;
    public final String url;
    public final String password;
    public final Integer ttl;

    public TurnEntry(String username, String password, int ttl, String url) {
        this.username = username;
        this.url = url;
        this.password = password;
        this.ttl = ttl;
    }

    public static TurnEntry fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, TurnEntry.class);
    }

}
