package org.bigbluebutton.messages.vo;

public class RecordingPropertiesBody {
    public final Boolean recorded;
    public final Boolean autoStartRecording;
    public final Boolean allowStartStopRecording;

    public RecordingPropertiesBody(Boolean recorded, Boolean autoStartRecording, Boolean allowStartStopRecording) {
        this.recorded = recorded;
        this.allowStartStopRecording = allowStartStopRecording;
        this.autoStartRecording = autoStartRecording;
    }
}
