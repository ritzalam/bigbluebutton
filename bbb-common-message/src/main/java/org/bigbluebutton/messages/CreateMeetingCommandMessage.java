package org.bigbluebutton.messages;

import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.payload.MeetingProperties;

public class CreateMeetingCommandMessage extends AbstractMessage {
    public final static String NAME = "CreateMeetingCommandMessage";

    public final MessageHeader header;
    public final CreateMeetingCommandMessagePayload payload;

    public CreateMeetingCommandMessage(MessageHeader header,
                                       CreateMeetingCommandMessagePayload payload) {
        super();
        this.header = header;
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
