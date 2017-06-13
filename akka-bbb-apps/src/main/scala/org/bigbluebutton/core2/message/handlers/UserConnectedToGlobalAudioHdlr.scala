package org.bigbluebutton.core2.message.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.{ UserConnectedToGlobalAudio, UserListeningOnly }
import org.bigbluebutton.core.models._
import org.bigbluebutton.core.running.MeetingActor
import org.bigbluebutton.core2.MeetingStatus2x

trait UserConnectedToGlobalAudioHdlr {
  this: MeetingActor =>

  val outGW: OutMessageGateway

  def handleUserConnectedToGlobalAudio(msg: UserConnectedToGlobalAudio) {
    log.info("Handling UserConnectedToGlobalAudio: meetingId=" + props.meetingProp.intId + " userId=" + msg.userid)

    val user = Users.findWithIntId(msg.userid, liveMeeting.users)
    user foreach { u =>
      if (MeetingStatus2x.addGlobalAudioConnection(liveMeeting.status, msg.userid)) {
        for {
          uvo <- Users.joinedVoiceListenOnly(msg.userid, liveMeeting.users)
        } yield {
          log.info("UserConnectedToGlobalAudio: meetingId=" + props.meetingProp.intId + " userId=" + uvo.id + " user=" + uvo)
          outGW.send(new UserListeningOnly(props.meetingProp.intId, props.recordProp.record, uvo.id, uvo.listenOnly))
        }
      }
    }
  }
}
