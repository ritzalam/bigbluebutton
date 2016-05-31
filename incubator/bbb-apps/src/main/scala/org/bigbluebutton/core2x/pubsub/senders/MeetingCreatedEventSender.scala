package org.bigbluebutton.core2x.pubsub.senders

import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.api.{ Constants, MessageNames }
import org.bigbluebutton.core.messaging.Util
import org.bigbluebutton.core2x.api.OutGoingMessage.MeetingCreated

trait MeetingCreatedEventSender {

  val service: MessageSender

  def handleMeetingCreated(msg: MeetingCreated) {
    val json = meetingCreatedToJson(msg)
    service.send(MessagingConstants.FROM_MEETING_CHANNEL, json)
  }

  private def meetingCreatedToJson(msg: MeetingCreated): String = {
    val payload = new java.util.HashMap[String, Any]()
    payload.put(Constants.MEETING_ID, msg.meetingId.value)
    payload.put(Constants.EXTERNAL_MEETING_ID, msg.mProps.extId.value)
    payload.put(Constants.NAME, msg.mProps.name.value)
    payload.put(Constants.RECORDED, msg.mProps.recordingProp.recorded)
    payload.put(Constants.VOICE_CONF, msg.mProps.voiceConf.value)
    payload.put(Constants.DURATION, msg.mProps.duration)

    val header = Util.buildHeader(MessageNames.MEETING_CREATED, None)
    Util.buildJson(header, payload)
  }
}
