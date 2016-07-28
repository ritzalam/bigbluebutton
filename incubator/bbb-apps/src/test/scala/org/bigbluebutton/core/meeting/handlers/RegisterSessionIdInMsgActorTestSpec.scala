package org.bigbluebutton.core.meeting.handlers

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.{ MeetingTestFixtures, OutMessageGateway, StopSystemAfterAll, TestKitUsageSpec }
import org.bigbluebutton.core.api.IncomingMsg.RegisterSessionIdInMsg
import org.bigbluebutton.core.api.OutGoingMsg.UserRegisteredEvent2x
import org.bigbluebutton.core.domain.{ Clients, ComponentId, SessionId, SessionToken }
import org.bigbluebutton.core.json.{ IncomingEventBus2x, OutgoingEventBus }
import org.bigbluebutton.core.meeting.MeetingActor2x
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, MeetingStatus }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class RegisterSessionIdInMsgActorTestSpec extends TestKit(ActorSystem("RegisterSessionIdInMsgActorTestSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A MeetingActor" should {
    "Send a SessionIdRegisteredOutMsg when receiving RegisterSessionIdInMsg" in {
      within(500 millis) {
        val clients = new Clients
        val state: MeetingStateModel = new MeetingStateModel(bbbDevProps,
          abilities, clients, registeredUsers, users, chats, layouts, polls, whiteboards,
          presentations, breakoutRooms, captions, new MeetingStatus)
        val meetingActorRef = system.actorOf(MeetingActor2x.props(bbbDevProps, eventBus, outGW, state))
        val componentId = new ComponentId("apps")
        val sessionId = new SessionId("testSessionId")
        val sessionToken = new SessionToken("testSessionToken")
        val msg = new RegisterSessionIdInMsg(componentId, sessionId, sessionToken)
        meetingActorRef ! richardRegisterUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
      }
    }
  }

}
