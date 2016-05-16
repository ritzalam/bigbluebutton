package org.bigbluebutton.core

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.bus.{ IncomingEventBus, OutgoingEventBus }
import org.bigbluebutton.core.models._
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class MeetingActor2xTestsSpec extends TestKit(ActorSystem("MeetingActorTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures {

  val eventBus = new IncomingEventBus
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, "outgoingMessageChannel")

  "A MeetingActor" should {
    "Send a DisconnectUser when receiving ValitadateAuthTokenCommand and there is no registered user" in {
      within(500 millis) {
        val state: MeetingState = new MeetingState(
          piliProps,
          abilities,
          registeredUsers,
          users,
          chats,
          layouts,
          polls,
          whiteboards,
          presentations,
          breakoutRooms,
          captions)

        val meetingActorRef = system.actorOf(MeetingActor2x.props(piliProps, eventBus, outGW, state))
        meetingActorRef ! du30ValidateAuthTokenCommand
        expectMsgClass(classOf[DisconnectUser2x])
      }
    }
  }

  "A MeetingActor" should {
    "Send a UserRegisteredEvent when receiving UserRegisterCommand" in {
      within(500 millis) {
        val state: MeetingState = new MeetingState(piliProps,
          abilities,
          registeredUsers,
          users,
          chats,
          layouts,
          polls,
          whiteboards,
          presentations,
          breakoutRooms,
          captions)
        val meetingActorRef = system.actorOf(MeetingActor2x.props(piliProps, eventBus, outGW, state))
        meetingActorRef ! du30RegisterUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
      }
    }
  }

  "A MeetingActor" should {
    "Send a ValidateAuthTokenReply when receiving ValitadateAuthTokenCommand and there is registered user" in {
      within(500 millis) {
        val state: MeetingState = new MeetingState(piliProps,
          abilities,
          registeredUsers,
          users,
          chats,
          layouts,
          polls,
          whiteboards,
          presentations,
          breakoutRooms,
          captions)
        val meetingActorRef = system.actorOf(MeetingActor2x.props(piliProps, eventBus, outGW, state))
        meetingActorRef ! du30RegisterUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
        meetingActorRef ! du30ValidateAuthTokenCommand
        expectMsgClass(classOf[ValidateAuthTokenReply2x])
      }
    }
  }

  "A MeetingActor" should {
    "Send a UserJoinedEvent when receiving UserJoinCommand and there is registered user" in {
      within(500 millis) {
        val state: MeetingState = new MeetingState(piliProps,
          abilities,
          registeredUsers,
          users,
          chats,
          layouts,
          polls,
          whiteboards,
          presentations,
          breakoutRooms,
          captions)
        val meetingActorRef = system.actorOf(MeetingActor2x.props(piliProps, eventBus, outGW, state))
        meetingActorRef ! du30RegisterUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
        meetingActorRef ! du30ValidateAuthTokenCommand
        expectMsgClass(classOf[ValidateAuthTokenReply2x])
        meetingActorRef ! du30UserJoinCommand
        expectMsgPF() {
          case event: UserJoinedEvent2x =>
            assert(event.meetingId == piliIntMeetingId)
        }
      }
    }
  }
}
