package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class VoiceUser {

    public Boolean talking;
    public Boolean voiceLocked;
    public Boolean muted;
    public Boolean joined;
    public String callerName;
    public String callerNum;
    public String webUserID;
    public String voiceUserID;

    public VoiceUser(Boolean talking, Boolean voiceLocked, Boolean muted, Boolean joined, String
            callerName, String callerNum, String webUserID, String voiceUserID) {
        this.talking = talking;
        this.voiceLocked = voiceLocked;
        this.muted = muted;
        this.joined = joined;
        this.callerName = callerName;
        this.callerNum = callerNum;
        this.webUserID = webUserID;
        this.voiceUserID = voiceUserID;
    }

    public static VoiceUser fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, VoiceUser.class);
    }

}
