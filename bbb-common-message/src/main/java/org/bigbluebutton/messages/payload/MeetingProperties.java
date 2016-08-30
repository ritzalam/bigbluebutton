package org.bigbluebutton.messages.payload;


public class MeetingProperties {

    public final String id;
    public final String externalId;
    public final String name;
    public final String voiceConf;
    public final Integer duration;
    public final Integer maxUsers;
    public final Boolean allowVoiceOnly;
    public final Boolean isBreakout;
    public final MeetingExtensionProp extensionProp;
    public final MeetingRecordingProp recordingProp;

    public MeetingProperties(String id, String externalId, String name, String voiceConf, Integer duration,
                             Integer maxUsers, Boolean allowVoiceOnly, Boolean isBreakout,
                             MeetingExtensionProp extensionProp, MeetingRecordingProp recordingProp) {
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
