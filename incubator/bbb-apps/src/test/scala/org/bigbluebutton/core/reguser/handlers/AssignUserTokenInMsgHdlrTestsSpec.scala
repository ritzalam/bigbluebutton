package org.bigbluebutton.core.reguser.handlers

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core._
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ AssignUserSessionTokenInMsg2x, AssignUserSessionTokenInMsgBody2x }
import org.bigbluebutton.core.api.OutGoingMsg.UserSessionTokenAssignedOutMsg2x
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId, SessionToken }
import org.bigbluebutton.core.reguser.{ RegisteredUserActor, SessionTokens }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class AssignUserTokenInMsgHdlrTestsSpec extends TestKit(ActorSystem("AssignUserTokenInMsgHdlrTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  val meetingId = IntMeetingId("testMeetingId")
  val userId = IntUserId("testUserId")

  "A RegisteredActor" should {
    "Send a UserSessionTokenAssignedOutMsg when receiving AssignUserSessionTokenInMsg" in {
      within(500 millis) {

        val sessionToken = SessionToken("fooSessionToken")

        val actorRef = system.actorOf(RegisteredUserActor.props(meetingId.value, userId.value,
          new SessionTokens(), eventBus, outGW))
        val header = InMessageHeader("foo", "bar", None, None)
        val body = AssignUserSessionTokenInMsgBody2x(meetingId, userId, sessionToken)
        val msg = AssignUserSessionTokenInMsg2x(header, body)
        actorRef ! msg
        expectMsgClass(classOf[UserSessionTokenAssignedOutMsg2x])
      }
    }
  }
}
