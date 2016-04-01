package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api._
import org.bigbluebutton.core.domain._

trait UsersMessageSender {
  this: LiveMeeting =>
  val outGW: OutMessageGateway

  def sendUerLeftMessage(meetingId: IntMeetingId, recorded: Recorded, user: UserVO) {
    outGW.send(new UserLeft(meetingId, recorded, user))
  }

  def sendUserListeningOnlyMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, listenOnly: ListenOnly) {
    outGW.send(new UserListeningOnly(meetingId, recorded, userId, listenOnly.value))
  }

  def sendMuteVoiceUserMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, requesterId: IntUserId,
    voiceId: VoiceUserId, voiceBridge: VoiceConf, mute: Boolean) {
    outGW.send(new MuteVoiceUser(meetingId, recorded, requesterId, userId, voiceBridge, voiceId, mute))
  }

  def sendMeetingMutedMessage(meetingId: IntMeetingId, recorded: Recorded, muted: Boolean) {
    outGW.send(new MeetingMuted(meetingId, recorded, muted))
  }

  def sendValidateAuthTokenReplyMessage(meetingId: IntMeetingId, userId: IntUserId, authToken: AuthToken,
    valid: Boolean, correlationId: String) {
    outGW.send(new ValidateAuthTokenReply(meetingId, userId, authToken, valid, correlationId))
  }

  def sendUserRegisteredMessage(meetingId: IntMeetingId, recorded: Recorded, regUser: RegisteredUser) {
    outGW.send(new UserRegistered(meetingId, recorded, regUser))
  }

  def sendIsMeetingMutedReplyMessage(meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, muted: Boolean) {
    outGW.send(new IsMeetingMutedReply(meetingId, recorded, requesterId, muted))
  }

  def sendEjectVoiceUserMessage(meetingId: IntMeetingId, recorded: Recorded, ejectedBy: IntUserId, userId: IntUserId,
    voiceId: VoiceUserId, voiceConf: VoiceConf) {
    outGW.send(new EjectVoiceUser(meetingId, recorded, ejectedBy, userId, voiceConf, voiceId))
  }

  def sendNewPermissionsSettingMessage(meetingId: IntMeetingId, userId: IntUserId, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new NewPermissionsSetting(meetingId, userId, permissions, users))
  }

  def sendUserLockedMessage(meetingId: IntMeetingId, userId: IntUserId, lock: Locked) {
    outGW.send(new UserLocked(meetingId, userId.value, lock.value))
  }

  def sendPermissionsSettingInitializedMessage(meetingId: IntMeetingId, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new PermissionsSettingInitialized(meetingId, permissions, users))
  }

  def sendUserChangedEmojiStatusMessage(meetingId: IntMeetingId, recorded: Recorded,
    emojiStatus: EmojiStatus, userId: IntUserId) {
    outGW.send(new UserChangedEmojiStatus(meetingId, recorded, emojiStatus, userId))
  }

  def sendUserEjectedFromMeetingMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, ejectedBy: IntUserId) {
    outGW.send(new UserEjectedFromMeeting(meetingId, recorded, userId, ejectedBy))
  }

  def sendDisconnectUserMessage(meetingId: IntMeetingId, userId: IntUserId) {
    outGW.send(new DisconnectUser(meetingId, userId))
  }

  def sendUserLeftMessage(meetingId: IntMeetingId, recorded: Recorded, user: UserVO) {
    outGW.send(new UserLeft(meetingId, recorded, user))
  }

  def sendUserSharedWebcamMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, stream: String) {
    outGW.send(new UserSharedWebcam(meetingId, recorded, userId, stream))
  }

  def sendUserUnsharedWebcamMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, stream: String) {
    outGW.send(new UserUnsharedWebcam(meetingId, recorded, userId, stream))
  }

  def sendUserStatusChangeMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, status: String, value: Object) {
    outGW.send(new UserStatusChange(meetingId, recorded, userId, status, value))
  }

  def sendGetUsersReplyMessage(meetingId: IntMeetingId, requesterId: IntUserId, users: Array[UserVO]) {
    outGW.send(new GetUsersReply(meetingId, requesterId, users))
  }

  def sendUserJoinedMessage(meetingId: IntMeetingId, recorded: Recorded, user: UserVO) {
    outGW.send(new UserJoined(meetingId, recorded, user))
  }

  def sendMeetingStateMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId,
    permissions: Permissions, muted: Muted) {
    outGW.send(new MeetingState(meetingId, recorded, userId, permissions, muted.value))
  }

  def sendUserLeftVoiceMessage(meetingId: IntMeetingId, recorded: Recorded, voiceConf: VoiceConf, user: UserVO) {
    outGW.send(new UserLeftVoice(meetingId, recorded, voiceConf, user))
  }

  def sendUserJoinedVoiceMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf, user: UserVO) {
    outGW.send(new UserJoinedVoice(meetingId, recorded, voiceBridge, user))
  }

  def sendStartRecordingVoiceConf(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf) {
    outGW.send(new StartRecordingVoiceConf(meetingId, recorded, voiceBridge))
  }

  def sendStopRecordingVoiceConf(meetingId: IntMeetingId, recorded: Recorded, voiceConf: VoiceConf, recordingFile: String) {
    outGW.send(new StopRecordingVoiceConf(meetingId, recorded, voiceConf, recordingFile))
  }

  def sendUserVoiceMutedMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf, user: UserVO) {
    outGW.send(new UserVoiceMuted(meetingId, recorded, voiceBridge, user))
  }

  def sendUserVoiceTalkingMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf, user: UserVO) {
    outGW.send(new UserVoiceTalking(meetingId, recorded, voiceBridge, user))
  }

  def sendUserStatusChangeMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, isPresenter: Boolean) {
    outGW.send(new UserStatusChange(meetingId, recorded, userId, "presenter", isPresenter: java.lang.Boolean))
  }

  def sendPresenterAssignedMessage(meetingId: IntMeetingId, recorded: Recorded, presenter: Presenter) {
    outGW.send(new PresenterAssigned(meetingId, recorded, presenter))
  }

  def sendMeetingHasEnded(meetingId: IntMeetingId, userId: IntUserId) {
    outGW.send(new MeetingHasEnded(meetingId, userId))
    outGW.send(new DisconnectUser(meetingId, userId))
  }
}