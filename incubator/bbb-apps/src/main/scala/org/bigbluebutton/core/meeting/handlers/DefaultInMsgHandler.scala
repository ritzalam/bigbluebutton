package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.api.IncomingMsg.InMsg
import org.bigbluebutton.core.meeting.MeetingActorMsg

trait DefaultInMsgHandler {
  this: MeetingActorMsg =>

  def handle(msg: InMsg): Unit = {
    log.warning("Unhandled message: " + msg.toString)
  }
}
