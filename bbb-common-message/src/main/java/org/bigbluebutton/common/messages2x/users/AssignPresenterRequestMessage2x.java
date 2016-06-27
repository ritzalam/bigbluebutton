package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class AssignPresenterRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "AssignPresenterRequestMessage";
    public final Payload payload;

    public AssignPresenterRequestMessage2x(String meetingID, String newPresenterUserID,
                                           String newPresenterName, String assignedBy) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.newPresenterUserID = newPresenterUserID;
        payload.newPresenterName = newPresenterName;
        payload.assignedBy = assignedBy;
    }

    public static AssignPresenterRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, AssignPresenterRequestMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String newPresenterUserID;
        public String newPresenterName;
        public String assignedBy;
    }

}
