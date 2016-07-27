package org.bigbluebutton.core2x.json.rx

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core2x.RedisMsgRxActor
import org.bigbluebutton.core2x.api.IncomingMsg.CreateMeetingRequestInMsg
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.messages.CreateMeetingRequestMessage
import org.bigbluebutton.messages.vo.{ ExtensionPropertiesBody, MeetingPropertiesBody, RecordingPropertiesBody }

trait CreateMeetingRequestJsonMsgRx extends UnhandledJsonMsgRx with SystemConfiguration {
  this: RedisMsgRxActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {

    def extractRecordingProperties(recProps: RecordingPropertiesBody): Option[MeetingRecordingProp] = {
      for {
        recorded <- Option(recProps.recorded)
        autoStartRecording <- Option(recProps.autoStartRecording)
        allowStartStopRecording <- Option(recProps.allowStartStopRecording)
      } yield new MeetingRecordingProp(Recorded(recorded), autoStartRecording, allowStartStopRecording)
    }

    def extractExtensionProperties(props: ExtensionPropertiesBody): Option[MeetingExtensionProp2x] = {
      for {
        maxExtensions <- Option(props.maxExtensions)
        extendByMinutes <- Option(props.extendByMinutes)
        sendNotice <- Option(props.sendNotice)
      } yield new MeetingExtensionProp2x(maxExtensions, extendByMinutes, sendNotice)
    }

    def extractMeetingProperties(props: MeetingPropertiesBody,
      extProps: ExtensionPropertiesBody,
      recProps: RecordingPropertiesBody): Option[MeetingProperties2x] = {
      for {
        recordingProps <- extractRecordingProperties(recProps)
        extensionProps <- extractExtensionProperties(extProps)
        id <- Option(props.id)
        extId <- Option(props.externalId)
        name <- Option(props.name)
        voiceConf <- Option(props.voiceConf)
        duration <- Option(props.duration)
        maxUsers <- Option(props.maxUsers)
        allowVoiceOnly <- Option(props.allowVoiceOnly)
        isBreakout <- Option(props.isBreakout)
      } yield new MeetingProperties2x(
        IntMeetingId(id),
        ExtMeetingId(extId),
        Name(name),
        VoiceConf(voiceConf),
        duration,
        maxUsers,
        allowVoiceOnly,
        isBreakout,
        extensionProps,
        recordingProps)
    }

    def publish(props: MeetingProperties2x): Unit = {
      eventBus.publish(
        BigBlueButtonInMessage(meetingManagerChannel, new CreateMeetingRequestInMsg(props.id, props)))
    }

    if (msg.name == CreateMeetingRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = CreateMeetingRequestMessage.fromJson(msg.data)
      for {
        props <- Option(m.body.props)
        extProps <- Option(props.extensionProp)
        recProps <- Option(props.recordingProp)
        mProps <- extractMeetingProperties(props, extProps, recProps)
      } yield publish(mProps)
    } else {
      super.handleReceivedJsonMsg(msg)
    }
  }
}
