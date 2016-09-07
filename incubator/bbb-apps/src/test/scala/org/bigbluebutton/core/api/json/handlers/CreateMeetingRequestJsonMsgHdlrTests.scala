package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ CreateMeetingRequestInMsg2x, CreateMeetingRequestInMsgBody }
import org.bigbluebutton.core.api.json.{ InJsonMsgProtocol }
import org.bigbluebutton.core.domain._
import org.bigbluebutton.messages.CreateMeetingRequestMessage
import org.bigbluebutton.messages.CreateMeetingRequestMessage.CreateMeetingRequestMessageBody
import org.bigbluebutton.messages.body.MessageHeader
import org.bigbluebutton.messages.vo.{ ExtensionPropertiesBody, MeetingPropertiesBody, RecordingPropertiesBody }

class CreateMeetingRequestJsonMsgHdlrTests extends UnitSpec {

  import spray.json._

  object TestProtocol1 extends DefaultJsonProtocol with InJsonMsgProtocol

  it should "convert json message to scala case class" in {
    import TestProtocol1._

    val id = "internal_meeting_id"
    val externalId = "external-meeting-id"
    val name = "meeting name"
    val voiceConf = "85115"
    val duration = 180
    val maxUsers = 25
    val allowVoiceOnly = false
    val isBreakout = false
    val maxExtensions = 3
    val extendByMinutes = 30
    val sendNotice = true
    val recorded = true
    val autoStartRecording = false
    val allowStartStopRecording = true

    val recordingProp: RecordingPropertiesBody = new RecordingPropertiesBody(recorded, autoStartRecording, allowStartStopRecording)
    val extensionProp: ExtensionPropertiesBody = new ExtensionPropertiesBody(maxExtensions, extendByMinutes, sendNotice)
    val meetingPropsBody: MeetingPropertiesBody = new MeetingPropertiesBody(id, externalId, name, voiceConf,
      duration, maxUsers, allowVoiceOnly, isBreakout, extensionProp, recordingProp)
    val body: CreateMeetingRequestMessageBody = new CreateMeetingRequestMessageBody(meetingPropsBody)

    val messageName = "CreateMeetingRequestMessage"
    val meetingId = id
    val senderId = "sender"
    val replyTo = null
    val header: MessageHeader = new MessageHeader(messageName, meetingId, senderId, replyTo)

    val msg: CreateMeetingRequestMessage = new CreateMeetingRequestMessage(header, body)

    println("* 1 \n" + msg.toJson)

    val json: JsObject = JsonParser(msg.toJson).asJsObject

    println("* 2 \n" + json)

    val inMsgFoo = json.convertTo[CreateMeetingRequestInMsg2x]

    println(inMsgFoo)

    assert(inMsgFoo.header.name == messageName)

    val inHeader = InMessageHeader(messageName, Some(meetingId), Some(senderId), None)
    println("* 3 \n" + inHeader.toJson)

    //val inJsonH = inHeader.toJson
    //println(inJsonH)

    val extProp = MeetingExtensionProp2x(maxExtensions, extendByMinutes, sendNotice)
    val recProp = MeetingRecordingProp(Recorded(recorded), autoStartRecording, allowStartStopRecording)
    val inProps = MeetingProperties2x(IntMeetingId(id), ExtMeetingId(externalId), Name(name), VoiceConf(voiceConf),
      duration, maxUsers, allowVoiceOnly, isBreakout, extProp, recProp)
    val inBody = CreateMeetingRequestInMsgBody(inProps)

    println("* 4 \n" + inBody.toJson)

    val inMsg = CreateMeetingRequestInMsg2x(inHeader, inBody)

    val inJson = inMsg.toJson
    println("* 5 \n" + inJson)
  }
}
