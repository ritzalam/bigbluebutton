package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.MeetingMuted
import org.bigbluebutton.core.api.UserListeningOnly
import org.bigbluebutton.core.api.MuteVoiceUser
import org.bigbluebutton.core.api.UserLeft
import org.bigbluebutton.core.models.UserVO
import org.bigbluebutton.core.api.ValidateAuthTokenReply
import org.bigbluebutton.core.api.UserRegistered
import org.bigbluebutton.core.models.RegisteredUser
import org.bigbluebutton.core.api.IsMeetingMutedReply
import org.bigbluebutton.core.api.EjectVoiceUser
import org.bigbluebutton.core.api.NewPermissionsSetting
import org.bigbluebutton.core.models.Permissions
import org.bigbluebutton.core.api.UserLocked
import org.bigbluebutton.core.api.PermissionsSettingInitialized
import org.bigbluebutton.core.api.UserChangedEmojiStatus
import org.bigbluebutton.core.api.UserEjectedFromMeeting
import org.bigbluebutton.core.api.DisconnectUser
import org.bigbluebutton.core.api.UserSharedWebcam
import org.bigbluebutton.core.api.UserUnsharedWebcam
import org.bigbluebutton.core.api.UserStatusChange
import org.bigbluebutton.core.api.GetUsersReply
import org.bigbluebutton.core.api.UserJoined
import org.bigbluebutton.core.api.MeetingState
import org.bigbluebutton.core.api.UserJoinedVoice
import org.bigbluebutton.core.api.StartRecordingVoiceConf
import org.bigbluebutton.core.api.UserVoiceMuted
import org.bigbluebutton.core.api.UserVoiceTalking
import org.bigbluebutton.core.api.PresenterAssigned
import org.bigbluebutton.core.models.Presenter

trait UsersMessageSender {
  this: LiveMeeting =>
  val outGW: OutMessageGateway

  def sendUerLeftMessage(meetingId: String, recorded: Boolean, user: UserVO) {
    outGW.send(new UserLeft(meetingId, recorded, user))
  }

  def sendUserListeningOnlyMessage(meetingId: String, recorded: Boolean, userId: String, listenOnly: Boolean) {
    outGW.send(new UserListeningOnly(meetingId, recorded, userId, listenOnly))
  }

  def sendMuteVoiceUserMessage(meetingId: String, recorded: Boolean, userId: String, requesterId: String,
    voiceId: String, voiceBridge: String, mute: Boolean) {
    outGW.send(new MuteVoiceUser(meetingId, recorded, requesterId, userId, voiceBridge, voiceId, mute))
  }

  def sendMeetingMutedMessage(meetingId: String, recorded: Boolean, muted: Boolean) {
    outGW.send(new MeetingMuted(meetingId, recorded, muted))
  }

  def sendValidateAuthTokenReplyMessage(meetingId: String, userId: String, authToken: String,
    valid: Boolean, correlationId: String) {
    outGW.send(new ValidateAuthTokenReply(meetingId, userId, authToken, valid, correlationId))
  }

  def sendUserRegisteredMessage(meetingId: String, recorded: Boolean, regUser: RegisteredUser) {
    outGW.send(new UserRegistered(meetingId, recorded, regUser))
  }

  def sendIsMeetingMutedReplyMessage(meetingId: String, recorded: Boolean, requesterId: String, muted: Boolean) {
    outGW.send(new IsMeetingMutedReply(meetingId, recorded, requesterId, muted))
  }

  def sendEjectVoiceUserMessage(meetingId: String, recorded: Boolean, ejectedBy: String, userId: String,
    voiceId: String, voiceBridge: String) {
    outGW.send(new EjectVoiceUser(meetingId, recorded, ejectedBy, userId, voiceBridge, voiceId))
  }

  def sendNewPermissionsSettingMessage(meetingId: String, userId: String, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new NewPermissionsSetting(meetingId, userId, permissions, users))
  }

  def sendUserLockedMessage(meetingId: String, userId: String, lock: Boolean) {
    outGW.send(new UserLocked(meetingId, userId, lock))
  }

  def sendPermissionsSettingInitializedMessage(meetingId: String, permissions: Permissions, users: Array[UserVO]) {
    outGW.send(new PermissionsSettingInitialized(meetingId, permissions, users))
  }

  def sendUserChangedEmojiStatusMessage(meetingId: String, recorded: Boolean, emojiStatus: String, userId: String) {
    outGW.send(new UserChangedEmojiStatus(meetingId, recorded, emojiStatus, userId))
  }

  def sendUserEjectedFromMeetingMessage(meetingId: String, recorded: Boolean, userId: String, ejectedBy: String) {
    outGW.send(new UserEjectedFromMeeting(meetingId, recorded, userId, ejectedBy))
  }

  def sendDisconnectUserMessage(meetingId: String, userId: String) {
    outGW.send(new DisconnectUser(meetingId, userId))
  }

  def sendUserLeftMessage(meetingId: String, recorded: Boolean, user: UserVO) {
    outGW.send(new UserLeft(meetingId, recorded, user))
  }

  def sendUserSharedWebcamMessage(meetingId: String, recorded: Boolean, userId: String, stream: String) {
    outGW.send(new UserSharedWebcam(meetingId, recorded, userId, stream))
  }

  def sendUserUnsharedWebcamMessage(meetingId: String, recorded: Boolean, userId: String, stream: String) {
    outGW.send(new UserUnsharedWebcam(meetingId, recorded, userId, stream))
  }

  def sendUserStatusChangeMessage(meetingId: String, recorded: Boolean, userId: String, status: String, value: Object) {
    outGW.send(new UserStatusChange(meetingId, recorded, userId, status, value))
  }

  def sendGetUsersReplyMessage(meetingId: String, requesterId: String, users: Array[UserVO]) {
    outGW.send(new GetUsersReply(meetingId, requesterId, users))
  }

  def sendUserJoinedMessage(meetingId: String, recorded: Boolean, user: UserVO) {
    outGW.send(new UserJoined(meetingId, recorded, user))
  }

  def sendMeetingStateMessage(meetingId: String, recorded: Boolean, userId: String, permissions: Permissions, muted: Boolean) {
    outGW.send(new MeetingState(meetingId, recorded, userId, permissions, muted))
  }

  def sendUserJoinedVoiceMessage(meetingId: String, recorded: Boolean, voiceBridge: String, user: UserVO) {
    outGW.send(new UserJoinedVoice(mProps.meetingID, mProps.recorded, mProps.voiceBridge, user))
  }

  def sendStartRecordingVoiceConf(meetingId: String, recorded: Boolean, voiceBridge: String) {
    outGW.send(new StartRecordingVoiceConf(meetingId, recorded, voiceBridge))
  }

  def sendUserVoiceMutedMessage(meetingId: String, recorded: Boolean, voiceBridge: String, user: UserVO) {
    outGW.send(new UserVoiceMuted(meetingId, recorded, voiceBridge, user))
  }

  def sendUserVoiceTalkingMessage(meetingId: String, recorded: Boolean, voiceBridge: String, user: UserVO) {
    outGW.send(new UserVoiceTalking(meetingId, recorded, voiceBridge, user))
  }

  def sendUserStatusChangeMessage(meetingId: String, recorded: Boolean, userId: String, isPresenter: Boolean) {
    outGW.send(new UserStatusChange(meetingId, recorded, userId, "presenter", isPresenter: java.lang.Boolean))
  }

  def sendPresenterAssignedMessage(meetingId: String, recorded: Boolean, presenter: Presenter) {
    outGW.send(new PresenterAssigned(mProps.meetingID, mProps.recorded, presenter))
  }
}