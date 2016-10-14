package org.bigbluebutton.core.meeting

import org.bigbluebutton.core.domain.IntUserId

object InMessage {
  trait MeetingInMsg

  case class MuteUserMeetingInMsg(userId: IntUserId, mute: Boolean) extends MeetingInMsg

}
