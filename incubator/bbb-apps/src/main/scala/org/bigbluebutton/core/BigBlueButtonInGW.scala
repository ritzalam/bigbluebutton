package org.bigbluebutton.core

import org.bigbluebutton.core.bus._
import org.bigbluebutton.core.api._
import akka.actor.ActorSystem
import org.bigbluebutton.common.messages.IBigBlueButtonMessage
import org.bigbluebutton.messages._
import akka.event.Logging
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core2x.api.IncomingMessage.CreateMeetingRequestInMessage
import org.bigbluebutton.core2x.domain.{ Permissions => _, Role => _, _ }
import org.bigbluebutton.core2x.BigBlueButtonActor2x
import org.bigbluebutton.core2x.bus._

class BigBlueButtonInGW(
    val system: ActorSystem,
    outGW: OutMessageGateway,
    eventBus2x: IncomingEventBus2x,
    incomingJsonMessageBus: IncomingJsonMessageBus) extends IBigBlueButtonInGW with SystemConfiguration {

  val log = Logging(system, getClass)

  val bbbActor2x = system.actorOf(BigBlueButtonActor2x.props(system, eventBus2x, outGW), "bigbluebutton-actor2x")
  eventBus2x.subscribe(bbbActor2x, meetingManagerChannel)

  def handleBigBlueButtonMessage(message: IBigBlueButtonMessage) {
    message match {

      case msg: CreateMeetingRequest => {
        val recProp = MeetingRecordingProp(
          Recorded(msg.payload.record),
          msg.payload.autoStartRecording,
          msg.payload.allowStartStopRecording)

        val mProps2x = new MeetingProperties2x(
          IntMeetingId(msg.payload.id),
          ExtMeetingId(msg.payload.externalId),
          Name(msg.payload.name),
          VoiceConf(msg.payload.voiceConfId),
          msg.payload.durationInMinutes,
          20,
          false,
          msg.payload.isBreakout,
          new MeetingExtensionProp2x(),
          recProp
        )

        eventBus2x.publish(BigBlueButtonInMessage(meetingManagerChannel, new CreateMeetingRequestInMessage(IntMeetingId(msg.payload.id), mProps2x)))

      }
    }
  }

  def handleReceivedJsonMessage(name: String, json: String): Unit = {
    println("INGW: \n" + json)
    val receivedJsonMessage = new ReceivedJsonMessage(name, json)
    incomingJsonMessageBus.publish(IncomingJsonMessage("incoming-json-message", receivedJsonMessage))
  }

}
