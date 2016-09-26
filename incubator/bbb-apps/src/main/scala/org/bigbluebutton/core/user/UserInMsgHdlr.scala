package org.bigbluebutton.core.user

import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.domain.{ RegisteredUser, UserState }
import org.bigbluebutton.core.meeting.models.MeetingStateModel
import org.bigbluebutton.core.user.handlers.{ UserJoinMeetingMsgHdlr, ValidateAuthTokenMsgHdlr }

class UserInMsgHdlr(
  val user: RegisteredUser,
  val outGW: OutMessageGateway)
    extends SystemConfiguration
    with ValidateAuthTokenMsgHdlr
    with UserJoinMeetingMsgHdlr {

  val userState: UserState = new UserState(user)

  def receive(msg: InMsg, meeting: MeetingStateModel): Unit = {
    msg match {
      case m: ValidateAuthTokenInMessage => handle(m, meeting)
      case m: JoinMeetingUserInMsg2x => handle(m, meeting)
    }
  }
}
