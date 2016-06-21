package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.Presentation;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationConversionDoneMessage2x extends AbstractEventMessage {
    public static final String PRESENTATION_CONVERSION_DONE = "PresentationConversionDoneMessage";
    public final Payload payload;

    public PresentationConversionDoneMessage2x(String meetingID, PresentationCode code,
                                               MessageKey messageKey, Presentation presentation) {
        super();
        header.name = PRESENTATION_CONVERSION_DONE;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.code = code;
        payload.messageKey = messageKey;
        payload.presentation = presentation;
    }

    public static PresentationConversionDoneMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationConversionDoneMessage2x.class);
    }

    public class Payload {
        public Presentation presentation;
        public String meetingID;
        public PresentationCode code;
        public MessageKey messageKey;
    }
}
