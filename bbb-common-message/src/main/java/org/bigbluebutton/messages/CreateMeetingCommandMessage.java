package org.bigbluebutton.messages;


import org.bigbluebutton.messages.payload.MeetingProperties;

public class CreateMeetingCommandMessage {
    public final static String NAME = "CreateMeetingCommandMessage";

    public final Header header;
    public final CreateMeetingCommandMessagePayload payload;

    public CreateMeetingCommandMessage(CreateMeetingCommandMessagePayload payload) {
        this.header = new Header(NAME);
        this.payload = payload;
    }

    public static class CreateMeetingCommandMessagePayload {
        public final String id;
        public final MeetingProperties props;

        public CreateMeetingCommandMessagePayload(String id, MeetingProperties props) {
            this.id = id;
            this.props = props;
        }
    }
}
