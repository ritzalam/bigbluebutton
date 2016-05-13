package org.bigbluebutton.core

import akka.actor.{ ActorSystem }
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.bus.{ IncomingEventBus, OutgoingEventBus }
import org.scalatest.{ BeforeAndAfterAll, Matchers, WordSpecLike }

import scala.concurrent.duration._

class MeetingActor2xTestsSpec extends TestKit(ActorSystem("MeetingActor2xTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with BeforeAndAfterAll with MeetingTestFixtures {

  val eventBus = new IncomingEventBus
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, "outgoingMessageChannel")

  val meetingActorRef = system.actorOf(MeetingActor2x.props(props, eventBus, outGW))

  override def afterAll {
    shutdown(system)
  }

  "A MeetingActor" should {
    "Send a DisconnectUser when receiving ValitadateAuthTokenCommand and there is no registered user" in {
      within(500 millis) {
        meetingActorRef ! validateAuthTokenCommand
        expectMsgClass(classOf[DisconnectUser2x])
      }
    }
  }

  "A MeetingActor" should {
    "Send a UserRegisteredEvent when receiving UserRegisterCommand" in {
      within(500 millis) {
        meetingActorRef ! registerUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
      }
    }
  }

  "A MeetingActor" should {
    "Send a ValidateAuthTokenReply when receiving ValitadateAuthTokenCommand and there is registered user" in {
      within(500 millis) {
        meetingActorRef ! validateAuthTokenCommand
        expectMsgClass(classOf[ValidateAuthTokenReply2x])
      }
    }
  }

  "A MeetingActor" should {
    "Send a UserJoinedEvent when receiving UserJoinCommand and there is registered user" in {
      within(500 millis) {
        meetingActorRef ! userJoinCommand
        expectMsgClass(classOf[UserJoinedEvent2x])
      }
    }
  }
}
