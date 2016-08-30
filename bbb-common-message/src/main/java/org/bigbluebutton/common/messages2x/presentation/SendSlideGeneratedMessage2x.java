package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class SendSlideGeneratedMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendConversionCompletedMessage";
    public final Payload payload;

    public SendSlideGeneratedMessage2x(String meetingID, MessageKey messageKey,
                                       PresentationCode code, String presentationID,
                                       String presName, Integer numberOfPages,
                                       Integer pagesCompleted) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.messageKey = messageKey;
        payload.code = code;
        payload.presentationID = presentationID;
        payload.presName = presName;
        payload.numberOfPages = numberOfPages;
        payload.pagesCompleted = pagesCompleted;
    }

    public static SendSlideGeneratedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendSlideGeneratedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public MessageKey messageKey;
        public PresentationCode code;
        public String presentationID;
        public String presName;
        public Integer numberOfPages;
        public Integer pagesCompleted;
    }

}
