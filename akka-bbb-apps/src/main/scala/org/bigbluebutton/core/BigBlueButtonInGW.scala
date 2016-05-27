package org.bigbluebutton.core

import org.bigbluebutton.core.bus._
import org.bigbluebutton.core.api._
import scala.collection.JavaConversions._
import akka.actor.ActorSystem
import org.bigbluebutton.core.domain.AnnotationVO
import org.bigbluebutton.common.messages.IBigBlueButtonMessage
import org.bigbluebutton.common.messages.StartCustomPollRequestMessage
import org.bigbluebutton.common.messages.PubSubPingMessage
import org.bigbluebutton.messages._
import akka.event.Logging
import org.bigbluebutton.core.domain._

class BigBlueButtonInGW(
    val system: ActorSystem,
    eventBus: IncomingEventBus,
    outGW: OutMessageGateway,
    val red5DeskShareIP: String,
    val red5DeskShareApp: String) extends IBigBlueButtonInGW {

  val log = Logging(system, getClass)

  val bbbActor = system.actorOf(
    BigBlueButtonActor.props(system, eventBus, outGW), "bigbluebutton-actor")

  eventBus.subscribe(bbbActor, "meeting-manager")

  def handleBigBlueButtonMessage(message: IBigBlueButtonMessage) {
    message match {
      case msg: StartCustomPollRequestMessage =>
        eventBus.publish(BigBlueButtonEvent("meeting-manager",
          new StartCustomPollRequest(IntMeetingId(msg.payload.meetingId),
            IntUserId(msg.payload.requesterId), msg.payload.pollType, msg.payload.answers)))
      case msg: PubSubPingMessage =>
        eventBus.publish(BigBlueButtonEvent("meeting-manager",
          new PubSubPing(msg.payload.system, msg.payload.timestamp)))

      case msg: CreateMeetingRequest =>
        val mProps = new MeetingProperties(
          IntMeetingId(msg.payload.id),
          ExtMeetingId(msg.payload.externalId),
          Name(msg.payload.name),
          Recorded(msg.payload.record),
          VoiceConf(msg.payload.voiceConfId),
          msg.payload.durationInMinutes,
          msg.payload.autoStartRecording,
          msg.payload.allowStartStopRecording,
          msg.payload.moderatorPassword,
          msg.payload.viewerPassword,
          msg.payload.createTime,
          msg.payload.createDate,
          msg.payload.isBreakout)

        eventBus.publish(BigBlueButtonEvent("meeting-manager",
          new CreateMeeting(IntMeetingId(msg.payload.id), mProps)))
    }
  }

  def handleJsonMessage(json: String) {
    JsonMessageDecoder.decode(json) match {
      case Some(validMsg) => forwardMessage(validMsg)
      case None => log.error("Unhandled message: {}", json)
    }
  }

  def forwardMessage(msg: InMessage) = {
    msg match {
      case m: BreakoutRoomsListMessage => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case m: CreateBreakoutRooms => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case m: RequestBreakoutJoinURLInMessage => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case m: TransferUserToMeetingRequest => eventBus.publish(BigBlueButtonEvent(m.meetingId.value, m))
      case m: EndAllBreakoutRooms => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case _ => log.error("Unhandled message: {}", msg)
    }
  }

}