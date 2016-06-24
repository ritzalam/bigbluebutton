package org.bigbluebutton.common.messages2x.presentation;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.MessageKey;
import org.bigbluebutton.common.messages2x.objects.PresentationCode;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

// TODO - perhaps we can use only PresentationConversionDoneMessage2x??
public class SendConversionCompletedMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendConversionCompletedMessage";
    public final Payload payload;

    public SendConversionCompletedMessage2x(String meetingID, MessageKey messageKey,
                                            PresentationCode code, String presentationID,
                                            Integer numPages, String presName,
                                            String presBaseURL) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.messageKey = messageKey;
        payload.code = code;
        payload.presentationID = presentationID;
        payload.numPages = numPages;
        payload.presName = presName;
        payload.presBaseURL = presBaseURL;
    }

    public static SendConversionCompletedMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendConversionCompletedMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public MessageKey messageKey;
        public PresentationCode code;
        public String presentationID;
        public Integer numPages;
        public String presName;
        public String presBaseURL;
    }

}
