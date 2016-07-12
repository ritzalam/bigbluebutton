package org.bigbluebutton.messages.presentation;

import org.bigbluebutton.messages.AbstractMessage;
import org.bigbluebutton.messages.body.MessageHeader;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ResizeAndMovePageEventMessage extends AbstractMessage {
    public static final String NAME = "ResizeAndMovePageEventMessage";

    public final MessageHeader header;
    public final Body body;

    public ResizeAndMovePageEventMessage(MessageHeader header,
                                         ResizeAndMovePageEventMessage.Body body) {
        super();
        this.body = body;
        this.header = header;
    }

    public static ResizeAndMovePageEventMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ResizeAndMovePageEventMessage.class);
    }

    public static class Body {
        public Double xOffset;
        public Double yOffset;
        public String pageId;
        public Double widthRatio;
        public Double heightRatio;


        public Body(Double xOffset, Double yOffset, String pageId, Double widthRatio, Double
                heightRatio) {
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.pageId = pageId;
            this.widthRatio = widthRatio;
            this.heightRatio = heightRatio;
        }
    }

}
