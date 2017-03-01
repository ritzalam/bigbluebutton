package org.bigbluebutton.web.pubsub

import org.bigbluebutton.common.JsonMarshaller
import org.bigbluebutton.common.message.{CreateMeetingRequestMessage2x, CreateMeetingRequestMessageBody, CreateMeetingRequestMessageConst, Header}
import org.bigbluebutton.pubsub.redis.MessagePublisher
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar

class RedisPublisherTest extends UnitSpec with MockitoSugar {
  it should "send create meeting message" in {
    val header: Header = Header(CreateMeetingRequestMessageConst.NAME, CreateMeetingRequestMessageConst.CHANNEL)
    val body: CreateMeetingRequestMessageBody =
      new CreateMeetingRequestMessageBody("id", "externalId", "parentId", "name",
        record = true, voiceConfId = "85115", duration = 600,
        autoStartRecording = true, allowStartStopRecording = true,
        webcamsOnlyForModerator = true, moderatorPass = "MP",
        viewerPass = "AP", createTime = 10000L, createDate = "Now",
        isBreakout = false, sequence = 0)

    val mockOutGW = mock[MessagePublisher]

    // Create the class under test and pass the mock to it
    val classUnderTest = new RedisPublisher(mockOutGW)

    classUnderTest.createMeeting("id", "externalId", "parentId", "name",
      recorded = true, voiceBridge = "85115", duration = 600,
      autoStartRecording = true, allowStartStopRecording = true,
      webcamsOnlyForModerator = true, moderatorPass = "MP",
      viewerPass = "AP", createTime = 10000L, createDate = "Now",
      isBreakout = false, sequence = 0)

    val msg = CreateMeetingRequestMessage2x(header, body)

    val json = JsonMarshaller.marshall(msg)

    verify(mockOutGW).send(CreateMeetingRequestMessageConst.CHANNEL, json)
  }
}
