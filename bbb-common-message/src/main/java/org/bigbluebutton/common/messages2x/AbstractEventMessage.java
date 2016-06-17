package org.bigbluebutton.common.messages2x;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import java.util.concurrent.TimeUnit;

public abstract class AbstractEventMessage {

    public final Header header;

    public AbstractEventMessage() {
        this.header = new Header();
    }

    public String toJson() {
        System.out.println("*****toJson ");
        ObjectMapper mapper = JsonFactory.create();
        return mapper.writeValueAsString(this);
    }

    public class Header {
        public String name;
        public long timestamp = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()); //TODO
        public long current_time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime()); //TODO
        public String version = "0.2"; // can be overridden
    }

}
