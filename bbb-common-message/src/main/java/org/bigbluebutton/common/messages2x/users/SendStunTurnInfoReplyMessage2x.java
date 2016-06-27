package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.bigbluebutton.common.messages2x.objects.StunEntry;
import org.bigbluebutton.common.messages2x.objects.TurnEntry;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;

public class SendStunTurnInfoReplyMessage2x extends AbstractEventMessage {

    public static final String NAME = "SendStunTurnInfoReplyMessage";
    public final Payload payload;

    public SendStunTurnInfoReplyMessage2x(String meetingID, String requesterID,
                                          ArrayList<StunEntry> stuns, ArrayList<TurnEntry> turns) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.meetingID = meetingID;
        payload.requesterID = requesterID;
        payload.stuns = stuns;
        payload.turns = turns;
    }

    public static SendStunTurnInfoReplyMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, SendStunTurnInfoReplyMessage2x.class);
    }

    public class Payload {
        public String meetingID;
        public String requesterID;
        public ArrayList<StunEntry> stuns;
        public ArrayList<TurnEntry> turns;
    }

}
