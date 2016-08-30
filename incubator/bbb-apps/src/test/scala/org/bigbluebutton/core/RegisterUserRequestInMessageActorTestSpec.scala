package org.bigbluebutton.core.meeting.filters

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.{ MeetingTestFixtures, OutMessageGateway, StopSystemAfterAll, TestKitUsageSpec }
import org.bigbluebutton.core.api.OutGoingMsg.UserRegisteredEvent2x
import org.bigbluebutton.core.domain.Clients
import org.bigbluebutton.core.api.json.{ IncomingEventBus2x, OutgoingEventBus }
import org.bigbluebutton.core.meeting.MeetingActorMsg
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, MeetingStatus }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class RegisterUserRequestInMessageActorTestSpec extends TestKit(ActorSystem("RegisterUserRequestInMessageActorTestSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A MeetingActor" should {
    "Send a UserRegisteredEvent when receiving UserRegisterCommand" in {
      within(500 millis) {
        val clients = new Clients
        val state: MeetingStateModel = new MeetingStateModel(bbbDevProps,
          abilities, clients, registeredUsers, users, chats, layouts, polls, whiteboards,
          presentations, breakoutRooms, captions, new MeetingStatus)
        val meetingActorRef = system.actorOf(MeetingActorMsg.props(bbbDevProps, eventBus, outGW, state))
        meetingActorRef ! richardRegisterUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
      }
    }
  }

}
