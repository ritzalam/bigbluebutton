package org.bigbluebutton.messages.whiteboard;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendWbHistoryReq extends AbstractMessage {
    public static final String NAME = "SendWbHistoryReq";

    public final MessageHeader header;
    public final SendWbHistoryReqBody body;

    public SendWbHistoryReq(MessageHeader header,
                            SendWbHistoryReqBody body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static SendWbHistoryReq fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendWbHistoryReq.class);
    }

    public static class SendWbHistoryReqBody {
        public String whiteboardId;

        public SendWbHistoryReqBody(String whiteboardId) {
            this.whiteboardId = whiteboardId;
        }
    }

}
