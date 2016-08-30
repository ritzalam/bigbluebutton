package org.bigbluebutton.messages.payload;


public class MeetingRecordingProp {
    public final Boolean recorded;
    public final Boolean autoStartRecording;
    public final Boolean allowStartStopRecording;

    public MeetingRecordingProp(Boolean recorded, Boolean autoStartRecording, Boolean allowStartStopRecording) {
        this.recorded = recorded;
        this.autoStartRecording = autoStartRecording;
        this.allowStartStopRecording = allowStartStopRecording;
    }

}
