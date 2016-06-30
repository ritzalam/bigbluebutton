package org.bigbluebutton.messages.vo;



public class MeetingPropertiesBody {
    public final String id;
    public final String externalId;
    public final String name;
    public final String voiceConf;
    public final Integer duration;
    public final Integer maxUsers;
    public final Boolean allowVoiceOnly;
    public final Boolean isBreakout;
    public final ExtensionPropertiesBody extensionProp;
    public final RecordingPropertiesBody recordingProp;

    public MeetingPropertiesBody(String id, String externalId, String name, String voiceConf,
                                 Integer duration, Integer maxUsers, Boolean allowVoiceOnly,
                                 Boolean isBreakout, ExtensionPropertiesBody extensionProp,
                                 RecordingPropertiesBody recordingProp) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.voiceConf = voiceConf;
        this.duration = duration;
        this.maxUsers = maxUsers;
        this.allowVoiceOnly = allowVoiceOnly;
        this.isBreakout = isBreakout;
        this.extensionProp = extensionProp;
        this.recordingProp = recordingProp;
    }
}
