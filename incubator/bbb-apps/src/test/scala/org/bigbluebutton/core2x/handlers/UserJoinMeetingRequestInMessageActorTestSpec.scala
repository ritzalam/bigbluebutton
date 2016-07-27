package org.bigbluebutton.core2x.handlers

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.bus.OutgoingEventBus
import org.bigbluebutton.core.{ OutMessageGateway, StopSystemAfterAll, TestKitUsageSpec }
import org.bigbluebutton.core2x.api.OutGoingMsg.{ PresenterAssignedEventOutMsg, UserJoinedEvent2x, UserRegisteredEvent2x, ValidateAuthTokenSuccessReplyOutMsg }
import org.bigbluebutton.core2x.json.IncomingEventBus2x
import org.bigbluebutton.core2x.domain.Clients
import org.bigbluebutton.core2x.models.{ MeetingStateModel, MeetingStatus }
import org.bigbluebutton.core2x.{ MeetingActor2x, MeetingTestFixtures }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class UserJoinMeetingRequestInMessageActorTestSpec extends TestKit(ActorSystem("MeetingActorTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A MeetingActor" should {
    "Send a UserJoinedEvent when receiving UserJoinCommand and there is registered user" in {
      within(500 millis) {
        val clients = new Clients
        val state: MeetingStateModel = new MeetingStateModel(bbbDevProps,
          abilities, clients, registeredUsers, users, chats, layouts, polls, whiteboards,
          presentations, breakoutRooms, captions, new MeetingStatus)

        val meetingActorRef = system.actorOf(MeetingActor2x.props(bbbDevProps, eventBus, outGW, state))
        meetingActorRef ! richardRegisterUserCommand
        expectMsgClass(classOf[UserRegisteredEvent2x])
        meetingActorRef ! richardValidateAuthTokenCommand
        expectMsgClass(classOf[ValidateAuthTokenSuccessReplyOutMsg])
        meetingActorRef ! richardUserJoinCommand
        //expectMsgAllClassOf(classOf[UserJoinedEvent2x], classOf[PresenterChangedEventOutMessage])
        expectMsgPF() {
          case event: UserJoinedEvent2x =>
            assert(event.meetingId == bbbDevIntMeetingId)
          case presEvent: PresenterAssignedEventOutMsg =>
            assert(presEvent.presenter.id == richardUserJoinCommand.senderId)
        }
        expectMsgPF() {
          case presEvent: PresenterAssignedEventOutMsg =>
            assert(presEvent.presenter.id == richardUserJoinCommand.senderId)
        }
        //expectMsgClass(classOf[PresenterChangedEventOutMessage])
      }
    }
  }

}
