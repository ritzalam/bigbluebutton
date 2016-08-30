package org.bigbluebutton.messages.payload;


public class MeetingExtensionProp {
    public final Integer maxExtension;
    public final Integer extendByMinutes;
    public final Boolean sendNotice;

    public MeetingExtensionProp(Integer maxExtension, Integer extendByMinutes, Boolean sendNotice) {
        this.maxExtension = maxExtension;
        this.extendByMinutes = extendByMinutes;
        this.sendNotice = sendNotice;
    }
}
