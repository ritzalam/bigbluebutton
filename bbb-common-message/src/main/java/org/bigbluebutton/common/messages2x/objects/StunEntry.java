package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class StunEntry {

    public final String url;

    public StunEntry(String url) {
        this.url = url;
    }

    public static StunEntry fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, StunEntry.class);
    }

}
