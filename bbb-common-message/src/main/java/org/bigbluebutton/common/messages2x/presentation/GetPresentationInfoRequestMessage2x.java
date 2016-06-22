package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetPresentationInfoRequestMessage2x extends AbstractEventMessage {

    public static final String GET_PRESENTATION_INFO_REQUEST_MESSAGE =
            "GetPresentationInfoRequestMessage2x";
    public final Payload payload;

    public GetPresentationInfoRequestMessage2x(String meetingID, String requesterID, String replyTo) {
        super();
        header.name = GET_PRESENTATION_INFO_REQUEST_MESSAGE;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.replyTo = replyTo;
    }

    public static GetPresentationInfoRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetPresentationInfoRequestMessage2x.class);
    }

    public class Payload {
        public String replyTo;
        public String meetingID;
        public String requesterID;
    }
}
