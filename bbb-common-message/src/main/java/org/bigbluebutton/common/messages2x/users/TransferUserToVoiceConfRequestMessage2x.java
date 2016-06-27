package org.bigbluebutton.common.messages2x.users;

import org.bigbluebutton.common.messages2x.AbstractEventMessage;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class TransferUserToVoiceConfRequestMessage2x extends AbstractEventMessage {

    public static final String NAME = "TransferUserToVoiceConfRequestMessage";
    public final Payload payload;

    public TransferUserToVoiceConfRequestMessage2x(String voiceConf, String targetVoiceConf,
                                                   String voiceUserID) {
        super();
        header.name = NAME;

        this.payload = new Payload();
        payload.voiceConf = voiceConf;
        payload.targetVoiceConf = targetVoiceConf;
        payload.voiceUserID = voiceUserID;
    }

    public static TransferUserToVoiceConfRequestMessage2x fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, TransferUserToVoiceConfRequestMessage2x.class);
    }

    public class Payload {
        public String voiceConf;
        public String targetVoiceConf;
        public String voiceUserID;
    }

}
