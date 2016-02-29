package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.models._

trait UsersMessageSender {
  this: LiveMeeting =>
  val outGW: OutMessageGateway

  def sendUerLeftMessage(meetingId: IntMeetingId, recorded: Recorded, user: UserVO) {
    outGW.send(new UserLeft(meetingId.value, recorded.value, user))
  }

  def sendUserListeningOnlyMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, listenOnly: Boolean) {
    outGW.send(new UserListeningOnly(meetingId.value, recorded.value, userId, listenOnly))
  }

  def sendMuteVoiceUserMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, requesterId: String,
    voiceId: String, voiceBridge: String, mute: Boolean) {
    outGW.send(new MuteVoiceUser(meetingId.value, recorded.value, requesterId, userId, voiceBridge, voiceId, mute))
  }

  def sendMeetingMutedMessage(meetingId: IntMeetingId, recorded: Recorded, muted: Boolean) {
    outGW.send(new MeetingMuted(meetingId.value, recorded.value, muted))
  }

  def sendValidateAuthTokenReplyMessage(meetingId: IntMeetingId, userId: String, authToken: String,
    valid: Boolean, correlationId: String) {
    outGW.send(new ValidateAuthTokenReply(meetingId.value, userId, authToken, valid, correlationId))
  }

  def sendUserRegisteredMessage(meetingId: IntMeetingId, recorded: Recorded, regUser: RegisteredUser) {
    outGW.send(new UserRegistered(meetingId.value, recorded.value, regUser))
  }

  def sendIsMeetingMutedReplyMessage(meetingId: IntMeetingId, recorded: Recorded, requesterId: String, muted: Boolean) {
    outGW.send(new IsMeetingMutedReply(meetingId.value, recorded.value, requesterId, muted))
  }

  def sendEjectVoiceUserMessage(meetingId: IntMeetingId, recorded: Recorded, ejectedBy: String, userId: String,
    voiceId: String, voiceBridge: String) {
    outGW.send(new EjectVoiceUser(meetingId.value, recorded.value, ejectedBy, userId, voiceBridge, voiceId))
  }

  def sendNewPermissionsSettingMessage(meetingId: IntMeetingId, userId: String, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new NewPermissionsSetting(meetingId.value, userId, permissions, users))
  }

  def sendUserLockedMessage(meetingId: IntMeetingId, userId: String, lock: Boolean) {
    outGW.send(new UserLocked(meetingId.value, userId, lock))
  }

  def sendPermissionsSettingInitializedMessage(meetingId: IntMeetingId, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new PermissionsSettingInitialized(meetingId.value, permissions, users))
  }

  def sendUserChangedEmojiStatusMessage(meetingId: IntMeetingId, recorded: Recorded, emojiStatus: String, userId: String) {
    outGW.send(new UserChangedEmojiStatus(meetingId.value, recorded.value, emojiStatus, userId))
  }

  def sendUserEjectedFromMeetingMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, ejectedBy: String) {
    outGW.send(new UserEjectedFromMeeting(meetingId.value, recorded.value, userId, ejectedBy))
  }

  def sendDisconnectUserMessage(meetingId: IntMeetingId, userId: String) {
    outGW.send(new DisconnectUser(meetingId.value, userId))
  }

  def sendUserLeftMessage(meetingId: IntMeetingId, recorded: Recorded, user: UserVO) {
    outGW.send(new UserLeft(meetingId.value, recorded.value, user))
  }

  def sendUserSharedWebcamMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, stream: String) {
    outGW.send(new UserSharedWebcam(meetingId.value, recorded.value, userId, stream))
  }

  def sendUserUnsharedWebcamMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, stream: String) {
    outGW.send(new UserUnsharedWebcam(meetingId.value, recorded.value, userId, stream))
  }

  def sendUserStatusChangeMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, status: String, value: Object) {
    outGW.send(new UserStatusChange(meetingId.value, recorded.value, userId, status, value))
  }

  def sendGetUsersReplyMessage(meetingId: IntMeetingId, requesterId: String, users: Array[UserVO]) {
    outGW.send(new GetUsersReply(meetingId.value, requesterId, users))
  }

  def sendUserJoinedMessage(meetingId: IntMeetingId, recorded: Recorded, user: UserVO) {
    outGW.send(new UserJoined(meetingId.value, recorded.value, user))
  }

  def sendMeetingStateMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, permissions: Permissions, muted: Boolean) {
    outGW.send(new MeetingState(meetingId.value, recorded.value, userId, permissions, muted))
  }

  def sendUserJoinedVoiceMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: String, user: UserVO) {
    outGW.send(new UserJoinedVoice(meetingId.value, recorded.value, mProps.voiceBridge, user))
  }

  def sendStartRecordingVoiceConf(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: String) {
    outGW.send(new StartRecordingVoiceConf(meetingId.value, recorded.value, voiceBridge))
  }

  def sendUserVoiceMutedMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: String, user: UserVO) {
    outGW.send(new UserVoiceMuted(meetingId.value, recorded.value, voiceBridge, user))
  }

  def sendUserVoiceTalkingMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: String, user: UserVO) {
    outGW.send(new UserVoiceTalking(meetingId.value, recorded.value, voiceBridge, user))
  }

  def sendUserStatusChangeMessage(meetingId: IntMeetingId, recorded: Recorded, userId: String, isPresenter: Boolean) {
    outGW.send(new UserStatusChange(meetingId.value, recorded.value, userId, "presenter", isPresenter: java.lang.Boolean))
  }

  def sendPresenterAssignedMessage(meetingId: IntMeetingId, recorded: Recorded, presenter: Presenter) {
    outGW.send(new PresenterAssigned(meetingId.value, recorded.value, presenter))
  }

  def sendMeetingHasEnded(meetingId: IntMeetingId, userId: String) {
    outGW.send(new MeetingHasEnded(meetingId.value, userId))
    outGW.send(new DisconnectUser(meetingId.value, userId))
  }
}