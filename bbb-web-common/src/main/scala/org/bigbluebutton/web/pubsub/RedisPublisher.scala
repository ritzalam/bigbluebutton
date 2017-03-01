package org.bigbluebutton.web.pubsub

import org.bigbluebutton.api.messaging.IMessagingService2x
import org.bigbluebutton.common.{JsonMarshaller}
import org.bigbluebutton.common.message._
import org.bigbluebutton.pubsub.redis.MessagePublisher

class RedisPublisher(val sender: MessagePublisher) extends IMessagingService2x {


  def createMeeting(meetingID: String, externalMeetingID: String, parentMeetingID: String, meetingName: String,
                    recorded: java.lang.Boolean, voiceBridge: String, duration: java.lang.Integer,
                    autoStartRecording: java.lang.Boolean, allowStartStopRecording: java.lang.Boolean,
                    webcamsOnlyForModerator: java.lang.Boolean, moderatorPass: String,
                    viewerPass: String, createTime: java.lang.Long, createDate: String,
                    isBreakout: java.lang.Boolean, sequence: java.lang.Integer) = {

    val header: Header = Header(CreateMeetingRequestMessageConst.NAME, CreateMeetingRequestMessageConst.CHANNEL)

    val body: CreateMeetingRequestMessageBody =
      new CreateMeetingRequestMessageBody(meetingID, externalMeetingID, parentMeetingID, meetingName,
        recorded, voiceBridge, duration, autoStartRecording, allowStartStopRecording, webcamsOnlyForModerator,
        moderatorPass, viewerPass, createTime, createDate, isBreakout, sequence)

    val msg = CreateMeetingRequestMessage2x(header, body)

    val json = JsonMarshaller.marshall(msg)

    sender.send(msg.header.channel, json)
  }



  def sendKeepAlive(system: String, timestamp: java.lang.Long) = {

    val header = Header(PubSubPingMessage2xConst.NAME, PubSubPingMessage2xConst.CHANNEL)
    val body = PubSubPingMessageBody(system, System.currentTimeMillis())
    val msg = PubSubPingMessage2x(header, body)

    val json = JsonMarshaller.marshall(msg)
    sender.send(msg.header.channel, json)
  }

}