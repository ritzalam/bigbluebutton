package org.bigbluebutton.core.user.client.handlers

import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.domain.SessionToken
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, MeetingStatus }
import org.bigbluebutton.core.reguser.RegisteredUsersModel
import org.bigbluebutton.core.user.UsersModel
import org.bigbluebutton.core.user.client.{ ClientInMsgHdlr, Clients, FlashWebUserAgent }
import org.bigbluebutton.core.{ MeetingTestFixtures, OutMessageGateway, UnitSpec }
import org.scalatest.mock.MockitoSugar

class UserJoinMeetingInMsgHdlrTests extends UnitSpec with MockitoSugar with MeetingTestFixtures {
  it should "eject user if user has ability" in {

    val testRegUsers = new RegisteredUsersModel
    testRegUsers.add(richardRegisteredUser)
    testRegUsers.add(fredRegisteredUser)
    testRegUsers.add(antonRegisteredUser)

    val testUsers = new UsersModel
    testUsers.save(richardUser)
    testUsers.save(fredUser)
    testUsers.save(antonUser)

    val clients = new Clients

    val state: MeetingStateModel = new MeetingStateModel(bbbDevProps, abilities, clients, testRegUsers,
      testUsers, chats, layouts, polls, whiteboards, presentations, breakoutRooms, captions, new MeetingStatus)

    val mockOutGW = mock[OutMessageGateway]

    val sessionToken = new SessionToken("foo")

    // Create the class under test and pass the mock to it
    val classUnderTest = new ClientInMsgHdlr(sessionToken, mockOutGW)

    val header = InMessageHeader("foo", "bar", None, None)
    val body = JoinMeetingUserInMsgBody2x(bbbDevProps.id, richardRegisteredUser.id, sessionToken, FlashWebUserAgent)
    val msg = JoinMeetingUserInMsg2x(header, body)

    // Use the class under test
    classUnderTest.handle(msg, state)

    // Then verify the class under test used the mock object as expected
    // The disconnect user shouldn't be called as user has ability to eject another user
    //verify(mockOutGW, never()).send(new DisconnectUser2x(ejectUserMsg.meetingId, ejectUserMsg.ejectedBy))

  }
}
