package org.bigbluebutton.messages;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public abstract class AbstractMessage {

    public String toJson() {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.writeValueAsString(this);
    }

}
