package org.bigbluebutton.core2.message.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.{ UserDisconnectedFromGlobalAudio, UserLeft, UserListeningOnly }
import org.bigbluebutton.core.models.{ Users, Users2x, VoiceUsers }
import org.bigbluebutton.core.running.MeetingActor
import org.bigbluebutton.core2.MeetingStatus2x

trait UserDisconnectedFromGlobalAudioHdlr {
  this: MeetingActor =>

  val outGW: OutMessageGateway

  def handleUserDisconnectedFromGlobalAudio(msg: UserDisconnectedFromGlobalAudio) {
    log.info("Handling UserDisconnectedToGlobalAudio: meetingId=" + props.meetingProp.intId + " userId=" + msg.userid)

    Users.findWithIntId(msg.userid, liveMeeting.users) match {
      case Some(user) =>
        for {
          u <- Users.leftVoiceListenOnly(msg.userid, liveMeeting.users)
        } yield {
          log.info("Not web user. Send user left message. meetingId=" + props.meetingProp.intId + " userId=" + u.id + " user=" + u)
          outGW.send(new UserLeft(props.meetingProp.intId, props.recordProp.record, uvo))
        }
    }


    user foreach { u =>
      if (MeetingStatus2x.removeGlobalAudioConnection(liveMeeting.status, msg.userid)) {
        if (!u.joinedWeb) {
          for {
            uvo <- Users.userLeft(u.id, liveMeeting.users)
          } yield {
            log.info("Not web user. Send user left message. meetingId=" + props.meetingProp.intId + " userId=" + u.id + " user=" + u)
            outGW.send(new UserLeft(props.meetingProp.intId, props.recordProp.record, uvo))
          }
        } else {
          for {
            uvo <- Users.leftVoiceListenOnly(u.id, liveMeeting.users)
          } yield {
            log.info("UserDisconnectedToGlobalAudio: meetingId=" + props.meetingProp.intId + " userId=" + uvo.id + " user=" + uvo)
            outGW.send(new UserListeningOnly(props.meetingProp.intId, props.recordProp.record, uvo.id, uvo.listenOnly))
          }
        }
      }
    }
  }
}
