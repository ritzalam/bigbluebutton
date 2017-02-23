package org.bigbluebutton.common

import org.bigbluebutton.common.message.{CreateMeetingRequestMessage2x, CreateMeetingRequestMessageConst, CreateMeetingRequestMessageBody, Header}

/**
  * Created by ralam on 2/23/2017.
  */
trait TestFixtures {
  val createMeetingHeader: Header = Header(CreateMeetingRequestMessageConst.NAME, CreateMeetingRequestMessageConst.CHANNEL)
  val createMeetingPayload: CreateMeetingRequestMessageBody =
    new CreateMeetingRequestMessageBody("id", "externalId", "parentId", "name",
      record = true, voiceConfId = "85115", duration = 600,
      autoStartRecording = true, allowStartStopRecording = true,
      webcamsOnlyForModerator = true, moderatorPass = "MP",
      viewerPass = "AP", createTime = 10000L, createDate = "Now",
      isBreakout = false, sequence = 0)

  val createMeetingRequestMsg = CreateMeetingRequestMessage2x(createMeetingHeader, createMeetingPayload)
}
