package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class ResizeAndMoveSlideMessage2x extends AbstractEventMessage {

    public static final String NAME = "ResizeAndMoveSlideMessage";
    public final Payload payload;

    public ResizeAndMoveSlideMessage2x(String meetingID, Double xOffset, Double yOffset,
                                       Double widthRatio, Double heightRatio) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.xOffset = xOffset;
        payload.yOffset = yOffset;
        payload.widthRatio = widthRatio;
        payload.heightRatio = heightRatio;
    }

    public static ResizeAndMoveSlideMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, ResizeAndMoveSlideMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public Double xOffset;
        public Double yOffset;
        public Double widthRatio;
        public Double heightRatio;
    }

}
