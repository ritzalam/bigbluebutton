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

  def sendUserListeningOnlyMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, listenOnly: ListenOnly) {
    outGW.send(new UserListeningOnly(meetingId.value, recorded.value, userId.value, listenOnly.value))
  }

  def sendMuteVoiceUserMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, requesterId: IntUserId,
    voiceId: VoiceUserId, voiceBridge: VoiceConf, mute: Boolean) {
    outGW.send(new MuteVoiceUser(meetingId.value, recorded.value, requesterId.value, userId.value,
      voiceBridge.value, voiceId.value, mute))
  }

  def sendMeetingMutedMessage(meetingId: IntMeetingId, recorded: Recorded, muted: Boolean) {
    outGW.send(new MeetingMuted(meetingId.value, recorded.value, muted))
  }

  def sendValidateAuthTokenReplyMessage(meetingId: IntMeetingId, userId: IntUserId, authToken: AuthToken,
    valid: Boolean, correlationId: String) {
    outGW.send(new ValidateAuthTokenReply(meetingId, userId, authToken, valid, correlationId))
  }

  def sendUserRegisteredMessage(meetingId: IntMeetingId, recorded: Recorded, regUser: RegisteredUser) {
    outGW.send(new UserRegistered(meetingId, recorded, regUser))
  }

  def sendIsMeetingMutedReplyMessage(meetingId: IntMeetingId, recorded: Recorded, requesterId: String, muted: Boolean) {
    outGW.send(new IsMeetingMutedReply(meetingId.value, recorded.value, requesterId, muted))
  }

  def sendEjectVoiceUserMessage(meetingId: IntMeetingId, recorded: Recorded, ejectedBy: IntUserId, userId: IntUserId,
    voiceId: VoiceUserId, voiceConf: VoiceConf) {
    outGW.send(new EjectVoiceUser(meetingId.value, recorded.value, ejectedBy.value,
      userId.value, voiceConf.value, voiceId.value))
  }

  def sendNewPermissionsSettingMessage(meetingId: IntMeetingId, userId: String, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new NewPermissionsSetting(meetingId.value, userId, permissions, users))
  }

  def sendUserLockedMessage(meetingId: IntMeetingId, userId: IntUserId, lock: Locked) {
    outGW.send(new UserLocked(meetingId.value, userId.value, lock.value))
  }

  def sendPermissionsSettingInitializedMessage(meetingId: IntMeetingId, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new PermissionsSettingInitialized(meetingId.value, permissions, users))
  }

  def sendUserChangedEmojiStatusMessage(meetingId: IntMeetingId, recorded: Recorded,
    emojiStatus: EmojiStatus, userId: IntUserId) {
    outGW.send(new UserChangedEmojiStatus(meetingId.value, recorded.value, emojiStatus.value, userId.value))
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

  def sendUserSharedWebcamMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, stream: String) {
    outGW.send(new UserSharedWebcam(meetingId.value, recorded.value, userId.value, stream))
  }

  def sendUserUnsharedWebcamMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, stream: String) {
    outGW.send(new UserUnsharedWebcam(meetingId.value, recorded.value, userId.value, stream))
  }

  def sendUserStatusChangeMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, status: String, value: Object) {
    outGW.send(new UserStatusChange(meetingId.value, recorded.value, userId.value, status, value))
  }

  def sendGetUsersReplyMessage(meetingId: IntMeetingId, requesterId: String, users: Array[UserVO]) {
    outGW.send(new GetUsersReply(meetingId.value, requesterId, users))
  }

  def sendUserJoinedMessage(meetingId: IntMeetingId, recorded: Recorded, user: UserVO) {
    outGW.send(new UserJoined(meetingId, recorded, user))
  }

  def sendMeetingStateMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId,
    permissions: Permissions, muted: Muted) {
    outGW.send(new MeetingState(meetingId.value, recorded.value, userId.value, permissions, muted.value))
  }

  def sendUserLeftVoiceMessage(meetingId: IntMeetingId, recorded: Recorded, voiceConf: VoiceConf, user: UserVO) {
    outGW.send(new UserLeftVoice(meetingId.value, recorded.value, voiceConf.value, user))
  }

  def sendUserJoinedVoiceMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf, user: UserVO) {
    outGW.send(new UserJoinedVoice(meetingId.value, recorded.value, voiceBridge.value, user))
  }

  def sendStartRecordingVoiceConf(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf) {
    outGW.send(new StartRecordingVoiceConf(meetingId.value, recorded.value, voiceBridge.value))
  }

  def sendStopRecordingVoiceConf(meetingId: IntMeetingId, recorded: Recorded, voiceConf: VoiceConf, recordingFile: String) {
    outGW.send(new StopRecordingVoiceConf(meetingId.value, recorded.value, voiceConf.value, recordingFile))
  }

  def sendUserVoiceMutedMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf, user: UserVO) {
    outGW.send(new UserVoiceMuted(meetingId.value, recorded.value, voiceBridge.value, user))
  }

  def sendUserVoiceTalkingMessage(meetingId: IntMeetingId, recorded: Recorded, voiceBridge: VoiceConf, user: UserVO) {
    outGW.send(new UserVoiceTalking(meetingId.value, recorded.value, voiceBridge.value, user))
  }

  def sendUserStatusChangeMessage(meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId, isPresenter: Boolean) {
    outGW.send(new UserStatusChange(meetingId.value, recorded.value, userId.value, "presenter", isPresenter: java.lang.Boolean))
  }

  def sendPresenterAssignedMessage(meetingId: IntMeetingId, recorded: Recorded, presenter: Presenter) {
    outGW.send(new PresenterAssigned(meetingId.value, recorded.value, presenter))
  }

  def sendMeetingHasEnded(meetingId: IntMeetingId, userId: String) {
    outGW.send(new MeetingHasEnded(meetingId.value, userId))
    outGW.send(new DisconnectUser(meetingId.value, userId))
  }
}