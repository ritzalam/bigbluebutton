package org.bigbluebutton.core.user.client

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.domain.SessionToken
import org.bigbluebutton.core.meeting.models.MeetingStateModel
import org.bigbluebutton.core.user.client.handlers.UserJoinMeetingMsgHdlr

class ClientInMsgHdlr(val sessionToken: SessionToken, val outGW: OutMessageGateway) extends SystemConfiguration
    with UserJoinMeetingMsgHdlr {

  def receive(msg: InMsg, meeting: MeetingStateModel): Unit = {
    msg match {
      case m: JoinMeetingUserInMsg2x => handle(m, meeting)
    }
  }
}
