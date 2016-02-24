package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.MeetingMuted
import org.bigbluebutton.core.api.UserListeningOnly
import org.bigbluebutton.core.api.MuteVoiceUser
import org.bigbluebutton.core.api.UserLeft
import org.bigbluebutton.core.models.UserVO

trait UsersMessageSender {
  this: LiveMeeting =>

  val outGW: OutMessageGateway

  def sendUerLeftMessage(user: UserVO) {
    outGW.send(new UserLeft(mProps.meetingID, mProps.recorded, user))
  }

  def sendUserListeningOnlyMessage(userId: String, listenOnly: Boolean) {
    outGW.send(new UserListeningOnly(mProps.meetingID, mProps.recorded, userId, listenOnly))
  }

  def sendMuteVoiceUserMessage(userId: String, requesterId: String, voiceId: String, mute: Boolean) {
    outGW.send(new MuteVoiceUser(mProps.meetingID, mProps.recorded, requesterId,
      userId, mProps.voiceBridge, voiceId, mute))
  }

  def sendMeetingMutedMessage() {
    outGW.send(new MeetingMuted(mProps.meetingID, mProps.recorded, meetingModel.isMeetingMuted()))
  }
}