package org.bigbluebutton.messages;


import org.bigbluebutton.messages.payload.MeetingExtensionProp;
import org.bigbluebutton.messages.payload.MeetingProperties;
import org.bigbluebutton.messages.payload.MeetingRecordingProp;
import org.junit.Assert;
import org.junit.Test;

public class CreateMeetingCommandMessageTest {

    @Test
    public void testCreateMeetingCommandMessageTest() {
        Integer maxExtension = 5;
        Integer extendByMinutes = 30;
        Boolean sendNotice = true;
        Boolean recorded = true;
        Boolean autoStartRecording = true;
        Boolean allowStartStopRecording = true;
        String id = "demoid";
        String externalId = "extDemoId";
        String name = "Demo Meeting";
        String voiceConf = "85115";
        Integer duration = 240;
        Integer maxUsers = 24;
        Boolean allowVoiceOnly = false;
        Boolean isBreakout = false;


        MeetingExtensionProp extensionProp = new MeetingExtensionProp(maxExtension, extendByMinutes, sendNotice);
        MeetingRecordingProp recordingProp = new MeetingRecordingProp(recorded, autoStartRecording, allowStartStopRecording);

        MeetingProperties props = new MeetingProperties(id, externalId, name, voiceConf, duration,
                maxUsers, allowVoiceOnly, isBreakout,
                extensionProp, recordingProp);

        CreateMeetingCommandMessage.CreateMeetingCommandMessagePayload payload =
                new CreateMeetingCommandMessage.CreateMeetingCommandMessagePayload(id, props);

        CreateMeetingCommandMessage msg = new CreateMeetingCommandMessage(payload);

        Assert.assertEquals(msg.header.name, CreateBreakoutRoomRequest.NAME);
        Assert.assertEquals(msg.payload.id, id);
        Assert.assertEquals(msg.payload.props.name, name);
        Assert.assertEquals(msg.payload.props.voiceConf, voiceConf);
        Assert.assertEquals(msg.payload.props.allowVoiceOnly, allowVoiceOnly);
        Assert.assertEquals(msg.payload.props.externalId, externalId);
        Assert.assertEquals(msg.payload.props.maxUsers, maxUsers);
        Assert.assertEquals(msg.payload.props.isBreakout, isBreakout);
    }
}
