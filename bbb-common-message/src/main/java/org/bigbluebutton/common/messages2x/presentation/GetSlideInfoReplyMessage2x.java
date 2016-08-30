package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.PresentationPage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetSlideInfoReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetSlideInfoReplyMessage";
    public final Payload payload;

    public GetSlideInfoReplyMessage2x(String meetingID, String requesterID,
                                      PresentationPage slide) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.slide = slide;
    }

    public static GetSlideInfoReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetSlideInfoReplyMessage2x.class);
    }

    public class Payload {
        public PresentationPage slide;
        public String meetingID;
        public String requesterID;
    }
}
