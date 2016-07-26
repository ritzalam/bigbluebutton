package org.bigbluebutton.core2x

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.{ OutMessageGateway, StopSystemAfterAll, TestKitUsageSpec }
import org.bigbluebutton.core2x.api.IncomingMsg._
import org.bigbluebutton.core.bus.OutgoingEventBus
import org.bigbluebutton.core2x.api.OutGoingMsg._
import org.bigbluebutton.core2x.bus.IncomingEventBus2x
import org.bigbluebutton.core2x.domain.Clients
import org.bigbluebutton.core2x.models.{ MeetingStateModel, MeetingStatus, RegisteredUsersModel, UsersModel }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class EjectUserFromMeetingMessageTestsSpec extends TestKit(ActorSystem("MeetingActorTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A MeetingActor" should {
    "Eject the user when receiving eject user command" in {
      within(500 millis) {
        val testRegUsers = new RegisteredUsersModel
        testRegUsers.add(richardRegisteredUser)
        testRegUsers.add(fredRegisteredUser)
        testRegUsers.add(antonRegisteredUser)

        val testUsers = new UsersModel
        testUsers.save(richardUser)
        testUsers.save(fredUser)
        testUsers.save(antonUser)

        val clients = new Clients

        val state: MeetingStateModel = new MeetingStateModel(bbbDevProps,
          abilities, clients, testRegUsers, testUsers, chats, layouts,
          polls, whiteboards, presentations, breakoutRooms, captions,
          new MeetingStatus)

        val ejectUserMsg = new EjectUserFromMeetingInMsg(bbbDevIntMeetingId, antonIntUserId, richardIntUserId)

        val meetingActorRef = system.actorOf(MeetingActor2x.props(bbbDevProps, eventBus, outGW, state))
        meetingActorRef ! ejectUserMsg
        //expectMsgAllClassOf(classOf[UserEjectedFromMeeting], classOf[DisconnectUser2x], classOf[UserLeft2x])
        expectMsgClass(classOf[UserEjectedFromMeetingEventOutMsg])
        expectMsgClass(classOf[DisconnectUser2x])
        expectMsgClass(classOf[UserLeftEventOutMsg])

        assert(state.usersModel.toVector.length == 2)
        assert(state.registeredUsersModel.toVector.length == 2)
      }
    }
  }
}
