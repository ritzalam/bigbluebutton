package org.bigbluebutton.common.messages2x.poll;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.PollType;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class CreatePollRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "CreatePollRequestMessage";
    public final Payload payload;

    public CreatePollRequestMessage2x(String meetingID, String requesterID, String pollID,
                                      PollType pollType) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.pollID = pollID;
        payload.pollType = pollType;
    }

    public static CreatePollRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, CreatePollRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public String pollID;
        public PollType pollType;
    }

}
