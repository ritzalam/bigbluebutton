package org.bigbluebutton.core.meeting.handlers

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core._
import org.bigbluebutton.core.api.IncomingMsg.CreateMeetingRequestInMsg
import org.bigbluebutton.core.api.OutGoingMsg.MeetingCreatedEventOutMsg
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, IncomingEventBus2x, OutgoingEventBus }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class CreateMeetingRequestInMessageActorTestSpec extends TestKit(ActorSystem("BigBlueButtonActorTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  "A BigBlueButtonActor" should {
    "Send meeting created event when receiving create meeting message" in {
      within(500 millis) {
        val bbbActorRef = system.actorOf(BigBlueButtonActor2x.props(system, eventBus, outGW))
        eventBus.subscribe(bbbActorRef, meetingManagerChannel)

        val msg = new CreateMeetingRequestInMsg(bbbDevProps.id, bbbDevProps)
        eventBus.publish(BigBlueButtonInMessage(meetingManagerChannel, msg))

        expectMsgClass(classOf[MeetingCreatedEventOutMsg])
      }
    }
  }
}
