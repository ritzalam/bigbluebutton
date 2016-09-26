package org.bigbluebutton.core.reguser.handlers

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core._
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ ValidateAuthTokenInMsg2x, ValidateAuthTokenInMsgBody }
import org.bigbluebutton.core.api.OutGoingMsg.ValidateAuthTokenReplyOutMsg2x
import org.bigbluebutton.core.reguser.SessionTokens
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.reguser.{ RegisteredUserActor, SessionTokens }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class ValidateAuthTokenInMsgHdlrTestsSpec extends TestKit(ActorSystem("ValidateAuthTokenInMsgHdlrTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)
  outgoingEventBus.subscribe(testActor, outgoingMessageChannel)

  val meetingId = IntMeetingId("testMeetingId")
  val userId = IntUserId("testUserId")

  "A RegisteredActor" should {
    "Send a ValidateAuthTokenReplyOutMsg when receiving ValidateAuthTokenInMsg with valid token" in {
      within(500 millis) {

        val fooToken = SessionToken("fooSessionToken")
        val sessionTokens = new SessionTokens()
        sessionTokens.add(fooToken)

        val actorRef = system.actorOf(RegisteredUserActor.props(meetingId.value, userId.value,
          sessionTokens, eventBus, outGW))
        val header = InMessageHeader("foo", "bar", None, None)
        val body = ValidateAuthTokenInMsgBody(fooToken, UserAgent("FlashWeb"), ComponentId("Apps"))
        val msg = ValidateAuthTokenInMsg2x(header, body)
        actorRef ! msg
        expectMsgPF() {
          case event: ValidateAuthTokenReplyOutMsg2x =>
            assert(event.body.body.valid == true)
        }
      }
    }
  }

  "A RegisteredActor" should {
    "Send a ValidateAuthTokenReplyOutMsg when receiving ValidateAuthTokenInMsg with invalid token" in {
      within(500 millis) {

        val fooToken = SessionToken("fooSessionToken")
        val sessionTokens = new SessionTokens()

        val actorRef = system.actorOf(RegisteredUserActor.props(meetingId.value, userId.value,
          sessionTokens, eventBus, outGW))
        val header = InMessageHeader("foo", "bar", None, None)
        val body = ValidateAuthTokenInMsgBody(fooToken, UserAgent("FlashWeb"), ComponentId("Apps"))
        val msg = ValidateAuthTokenInMsg2x(header, body)
        actorRef ! msg
        expectMsgPF() {
          case event: ValidateAuthTokenReplyOutMsg2x =>
            assert(event.body.body.valid == false)
        }
      }
    }
  }
}
