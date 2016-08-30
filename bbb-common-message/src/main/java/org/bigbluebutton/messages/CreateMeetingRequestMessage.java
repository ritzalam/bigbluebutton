package org.bigbluebutton.messages;

import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.vo.MeetingPropertiesBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class CreateMeetingRequestMessage extends AbstractMessage {
    public static final String NAME = "CreateMeetingRequestMessage";

    public final MessageHeader header;
    public final CreateMeetingRequestMessageBody body;

    public CreateMeetingRequestMessage(MessageHeader header,
                                       CreateMeetingRequestMessageBody body) {
        super();
        this.header = header;
        this.body = body;
    }

    public static class CreateMeetingRequestMessageBody {
        public final MeetingPropertiesBody props;

        public CreateMeetingRequestMessageBody(MeetingPropertiesBody props) {
            this.props = props;
        }
    }

    public static CreateMeetingRequestMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, CreateMeetingRequestMessage.class);
    }
}
