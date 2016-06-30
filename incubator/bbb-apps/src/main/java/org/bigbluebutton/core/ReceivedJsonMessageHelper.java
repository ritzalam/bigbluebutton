package org.bigbluebutton.core;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import java.util.Map;

public class ReceivedJsonMessageHelper {

    public String getMessageName(String json) {
        ObjectMapper mapper = JsonFactory.create();
        Map msg = mapper.readValue(json, Map.class);
        Map header = (Map) msg.get("header");

        String msgName = (String) header.get("name");
        return msgName;
    }
}
