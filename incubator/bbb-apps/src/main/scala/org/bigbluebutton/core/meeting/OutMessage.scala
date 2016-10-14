package org.bigbluebutton.core.meeting

import org.bigbluebutton.core.domain.IntUserId

object OutMessage {
  trait MeetingOutMsg

  case class UserMutedEventMsg(useId: IntUserId, muted: Boolean) extends MeetingOutMsg
}
