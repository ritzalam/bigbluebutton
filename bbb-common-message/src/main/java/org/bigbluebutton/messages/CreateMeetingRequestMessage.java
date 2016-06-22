package org.bigbluebutton.messages;


import org.bigbluebutton.messages.vo.MeetingPropertiesBody;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class CreateMeetingRequestMessage {
    public static final String NAME = "CreateMeetingRequestMessage";

    public final CreateMeetingRequestMessageHeader header;
    public final CreateMeetingRequestMessageBody body;

    public CreateMeetingRequestMessage(CreateMeetingRequestMessageHeader header, CreateMeetingRequestMessageBody body) {
        this.header = header;
        this.body = body;
    }

    public static class CreateMeetingRequestMessageHeader {
        public final String name;

        public CreateMeetingRequestMessageHeader(String name) {
            this.name = name;
        }
    }

    public static class CreateMeetingRequestMessageBody {
        public final MeetingPropertiesBody props;

        public CreateMeetingRequestMessageBody(MeetingPropertiesBody props) {
            this.props = props;
        }
    }

    public String toJson() {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.writeValueAsString(this);
    }

    public static CreateMeetingRequestMessage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, CreateMeetingRequestMessage.class);
    }




}
