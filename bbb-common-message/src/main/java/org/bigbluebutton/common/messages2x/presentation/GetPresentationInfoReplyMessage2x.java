package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.Presentation;
import org.bigbluebutton.common.messages2x.objects.Presenter;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class GetPresentationInfoReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "GetPresentationInfoReplyMessage";
    public final Payload payload;

    public GetPresentationInfoReplyMessage2x(String meetingID, String requesterID, Presenter
            presenter, Presentation[] presentations) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.presenter = presenter;
        payload.presentations = presentations;
    }

    public static GetPresentationInfoReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, GetPresentationInfoReplyMessage2x.class);
    }

    public class Payload {
        public Presenter presenter;
        public String meetingID;
        public String requesterID;
        public Presentation[] presentations;
    }
}
