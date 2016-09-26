package org.bigbluebutton.messages.whiteboard;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.Map;
import java.util.Vector;

public class SendWbHistoryReply extends AbstractMessage {
    public static final String NAME = "SendWbHistoryReply";

    public final MessageHeader header;
    public final SendWbHistoryReplyBody body;

    public SendWbHistoryReply(MessageHeader header,
                              SendWbHistoryReplyBody body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SendWbHistoryReply fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendWbHistoryReply.class);
    }

    public static class SendWbHistoryReplyBody {
        public Vector<Map<String, Object>> annotations;

        public SendWbHistoryReplyBody(Vector<Map<String, Object>> annotations) {
            this.annotations = annotations;
        }
    }

}
