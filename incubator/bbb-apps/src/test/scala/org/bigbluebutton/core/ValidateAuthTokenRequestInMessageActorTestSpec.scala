package org.bigbluebutton.core

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.api.OutGoingMsg.{ DisconnectUser2x, UserRegisteredEvent2x, ValidateAuthTokenSuccessReplyOutMsg }
import org.bigbluebutton.core.domain.Clients
import org.bigbluebutton.core.api.json.{ IncomingEventBus2x, OutgoingEventBus }
import org.bigbluebutton.core.meeting.MeetingActorMsg
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, MeetingStatus }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class ValidateAuthTokenRequestInMessageActorTestSpec extends TestKit(ActorSystem("ValidateAuthTokenRequestInMessageActorTestSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A MeetingActor" should {
    "Send a DisconnectUser when receiving ValitadateAuthTokenCommand and there is no registered user" in {
      within(500 millis) {
        val clients = new Clients
        val state: MeetingStateModel = new MeetingStateModel(
          bbbDevProps, abilities, clients, registeredUsers, users, chats, layouts, polls,
          whiteboards, presentations, breakoutRooms, captions, new MeetingStatus)

        val meetingActorRef = system.actorOf(MeetingActorMsg.props(bbbDevProps, eventBus, outGW, state))
        meetingActorRef ! richardValidateAuthTokenCommand
        expectMsgClass(classOf[DisconnectUser2x])
      }
    }
  }

  "A MeetingActor" should {
    "Send a ValidateAuthTokenReply when receiving ValitadateAuthTokenCommand and there is registered user" in {
      within(500 millis) {
        val clients = new Clients
        val state: MeetingStateModel = new MeetingStateModel(bbbDevProps,
          abilities, clients, registeredUsers, users, chats, layouts, polls, whiteboards,
          presentations, breakoutRooms, captions, new MeetingStatus)
        val meetingActorRef = system.actorOf(MeetingActorMsg.props(bbbDevProps, eventBus, outGW, state))
        meetingActorRef ! richardRegisterUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
        meetingActorRef ! richardValidateAuthTokenCommand
        expectMsgClass(classOf[ValidateAuthTokenSuccessReplyOutMsg])
      }
    }
  }
}
