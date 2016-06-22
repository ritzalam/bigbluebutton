package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationPageGeneratedMessage2x extends AbstractEventMessage {

    public static final String NAME = "PresentationPageGeneratedMessage2x";



    public final Payload payload;

    public PresentationPageGeneratedMessage2x(String meetingID, String presentationID, Integer
            numPages, PresentationCode code, MessageKey messageKey, String presentationName, Integer
            pagesCompleted) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.presentationID = presentationID;
        payload.numPages = numPages;
        payload.code = code;
        payload.messageKey = messageKey;
        payload.presentationName = presentationName;
        payload.pagesCompleted = pagesCompleted;
    }

    public static PresentationPageGeneratedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationPageGeneratedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String presentationID;
        public Integer numPages;
        public PresentationCode code;
        public MessageKey messageKey;
        public String presentationName;
        public Integer pagesCompleted;
    }
}
