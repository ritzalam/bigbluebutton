package org.bigbluebutton.web.pubsub

import org.bigbluebutton.api.messaging.{IMessagingService2x}
import org.bigbluebutton.common.JsonUtil
import org.bigbluebutton.common.message._
import org.bigbluebutton.pubsub.redis.MessagePublisher

class RedisPublisher(val sender: MessagePublisher) extends IMessagingService2x {


  def createMeeting(meetingID: String, externalMeetingID: String, parentMeetingID: String, meetingName: String,
                    recorded: java.lang.Boolean, voiceBridge: String, duration: java.lang.Integer,
                    autoStartRecording: java.lang.Boolean, allowStartStopRecording: java.lang.Boolean,
                    webcamsOnlyForModerator: java.lang.Boolean, moderatorPass: String,
                    viewerPass: String, createTime: java.lang.Long, createDate: String,
                    isBreakout: java.lang.Boolean, sequence: java.lang.Integer) = {
    val header: Header = Header(CreateMeetingRequestMessage2x.NAME, CreateMeetingRequestMessage2x.CHANNEL)

    val payload: CreateMeetingRequestMessagePayload =
      new CreateMeetingRequestMessagePayload(meetingID, externalMeetingID, parentMeetingID, meetingName,
        recorded, voiceBridge, duration, autoStartRecording, allowStartStopRecording, webcamsOnlyForModerator,
        moderatorPass, viewerPass, createTime, createDate, isBreakout, sequence)

    val msg = CreateMeetingRequestMessage2x(header, payload)
    val json  = JsonUtil.toJson(msg)

    sender.send(msg.header.channel, json)
  }



  def sendKeepAlive(system: String, timestamp: java.lang.Long) = {
    val header: Header = Header(PubSubPingMessage2x.NAME, PubSubPingMessage2x.CHANNEL)
    val payload: PubSubPingMessagePayload = PubSubPingMessagePayload(system, timestamp)

    val msg = PubSubPingMessage2x(header, payload)
    val json = JsonUtil.toJson(msg)
    sender.send(msg.header.channel, json)
  }

}