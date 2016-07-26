package org.bigbluebutton.core2x.bus.handlers.presentation

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.bus.OutgoingEventBus
import org.bigbluebutton.core.{ OutMessageGateway, StopSystemAfterAll, TestKitUsageSpec }
import org.bigbluebutton.core2x.bus.IncomingEventBus2x
import org.bigbluebutton.core2x.models.{ MeetingStateModel, MeetingStatus }
import org.bigbluebutton.core2x.{ MeetingActor2x, MeetingTestFixtures }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class SendCursorUpdateEventMessageHandlerTestSpec extends TestKit(ActorSystem("SendCursorUpdateEventMessage",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A MeetingActor" should {
    "Send a ?? when receiving SendCursorUpdateEventMessage" in { // TODO
      within(500 millis) {
        val state: MeetingStateModel = new MeetingStateModel(
          bbbDevProps, abilities, registeredUsers, users, chats, layouts, polls,
          whiteboards, presentations, breakoutRooms, captions, new MeetingStatus)

        val meetingActorRef = system.actorOf(MeetingActor2x.props(bbbDevProps, eventBus, outGW, state))
        meetingActorRef ! sendCursorUpdateCommand
        // expectMsgClass(classOf[PubSubPong]) // TODO
      }
    }
  }
}

