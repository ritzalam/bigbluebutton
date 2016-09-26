package org.bigbluebutton.core.meeting.handlers

import akka.actor.ActorSystem
import akka.testkit.{ DefaultTimeout, ImplicitSender, TestKit }
import com.typesafe.config.ConfigFactory
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core._
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg.{ AssignUserSessionTokenInMsg2x, AssignUserSessionTokenInMsgBody2x }
import org.bigbluebutton.core.api.OutGoingMsg.{ UserSessionTokenAssignedOutMsg2x, ValidateAuthTokenReplyOutMsg2x }
import org.bigbluebutton.core.apps.reguser.{ RegisteredUserActor, SessionTokens }
import org.bigbluebutton.core.client.Clients
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId, SessionToken }
import org.bigbluebutton.core.meeting.MeetingActorMsg
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, MeetingStatus }
import org.scalatest.{ Matchers, WordSpecLike }

import scala.concurrent.duration._

class AssignUserTokenInMsgHdlrTestsSpec extends TestKit(ActorSystem("AssignUserTokenInMsgHdlrTestsSpec",
  ConfigFactory.parseString(TestKitUsageSpec.config)))
    with DefaultTimeout with ImplicitSender with WordSpecLike
    with Matchers with StopSystemAfterAll with MeetingTestFixtures with SystemConfiguration {

  val eventBus = new IncomingEventBus2x
  val outgoingEventBus = new OutgoingEventBus
  val outGW = new OutMessageGateway(outgoingEventBus)

  val meetingId = IntMeetingId("testMeetingId")
  val userId = IntUserId("testUserId")

  "A MeetingActor" should {
    "Send a UserSessionTokenAssignedOutMsg when receiving AssignUserSessionTokenInMsg" in {
      within(500 millis) {

        val sessionToken = SessionToken("fooSessionToken")
        val clients = new Clients
        val state: MeetingStateModel = new MeetingStateModel(bbbDevProps,
          abilities, clients, registeredUsers, users, chats, layouts, polls, whiteboards,
          presentations, breakoutRooms, captions, new MeetingStatus)
        val meetingActorRef = system.actorOf(MeetingActorMsg.props(bbbDevProps, eventBus, outGW, state))

        val dest = userId.value + "@" + meetingId.value

        eventBus.subscribe(testActor, dest)

        val header = InMessageHeader("foo", "bar", None, None)
        val body = AssignUserSessionTokenInMsgBody2x(meetingId, userId, sessionToken)
        val msg = AssignUserSessionTokenInMsg2x(header, body)
        meetingActorRef ! msg
        expectMsgPF() {
          case event: AssignUserSessionTokenInMsg2x =>
            assert(event.header.dest == dest)
        }
      }
    }
  }
}
