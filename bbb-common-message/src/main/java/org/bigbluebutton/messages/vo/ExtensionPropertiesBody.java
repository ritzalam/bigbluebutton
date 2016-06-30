package org.bigbluebutton.messages.vo;

public class ExtensionPropertiesBody {
    public final Integer maxExtensions;
    public final Integer extendByMinutes;
    public final Boolean sendNotice;

    public ExtensionPropertiesBody(Integer maxExtensions, Integer extendByMinutes, Boolean sendNotice) {
        this.maxExtensions = maxExtensions;
        this.extendByMinutes = extendByMinutes;
        this.sendNotice = sendNotice;
    }
}