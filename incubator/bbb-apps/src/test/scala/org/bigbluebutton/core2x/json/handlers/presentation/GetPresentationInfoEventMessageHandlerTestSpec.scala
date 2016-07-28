package org.bigbluebutton.core2x.json.handlers.presentation

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core2x.json.{IncomingEventBus2x, OutgoingEventBus}
import org.bigbluebutton.core2x.domain.Clients
import org.bigbluebutton.core2x.meeting.models.{MeetingStateModel, MeetingStatus}
import org.bigbluebutton.core2x.{MeetingTestFixtures, OutMessageGateway, StopSystemAfterAll, TestKitUsageSpec}
import org.bigbluebutton.core2x.meeting.MeetingActor2x
import org.scalatest.{Matchers, WordSpecLike}

import scala.concurrent.duration._

class GetPresentationInfoEventMessageHandlerTestSpec extends TestKit(ActorSystem("GetPresentationInfoEventMessage",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A MeetingActor" should {
    "Send a ?? when receiving GetPresentationInfoEventMessage" in { // TODO
      within(500 millis) {
        val clients = new Clients
        val state: MeetingStateModel = new MeetingStateModel(
          bbbDevProps, abilities, clients, registeredUsers, users, chats, layouts, polls,
          whiteboards, presentations, breakoutRooms, captions, new MeetingStatus)

        val meetingActorRef = system.actorOf(MeetingActor2x.props(bbbDevProps, eventBus, outGW, state))
        meetingActorRef ! getPresentationInfoCommand
        // expectMsgClass(classOf[PubSubPong]) // TODO
      }
    }
  }
}

