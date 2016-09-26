package org.bigbluebutton.core.reguser.handlers

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core._
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ JoinMeetingUserInMsg2x, JoinMeetingUserInMsgBody2x }
import org.bigbluebutton.core.user.client.FlashWebUserAgent
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId, SessionToken }
import org.bigbluebutton.core.reguser.{ RegisteredUserActor, SessionTokens }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class JoinMeetingUserInMsgHdlrTestsSpec extends TestKit(ActorSystem("JoinMeetingUserInMsgHdlrTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)

  val meetingId = IntMeetingId("testMeetingId")
  val userId = IntUserId("testUserId")

  "A RegisteredActor" should {
    "Send a JoinMeetingUserInMsg to the Meeting when receiving JoinMeetingUserInMsg" in {
      within(500 millis) {

        val sessionToken = SessionToken("fooSessionToken")
        val sessionTokens = new SessionTokens()
        sessionTokens.add(sessionToken)

        eventBus.subscribe(testActor, meetingId.value)

        val actorRef = system.actorOf(RegisteredUserActor.props(meetingId.value, userId.value,
          sessionTokens, eventBus, outGW))
        val header = InMessageHeader("foo", "bar", None, None)
        val body = JoinMeetingUserInMsgBody2x(meetingId, userId, sessionToken, FlashWebUserAgent)
        val msg = JoinMeetingUserInMsg2x(header, body)
        actorRef ! msg
        expectMsgPF() {
          case event: JoinMeetingUserInMsg2x =>
            assert(event.header.dest == meetingId.value)
        }
      }
    }
  }
}
