package org.bigbluebutton.core2x.api

import org.bigbluebutton.core2x.apps.presentation.domain.PresentationId
import org.bigbluebutton.core2x.apps.presentation.{ Page, Presentation }
import org.bigbluebutton.core2x.domain._

object OutGoingMsg {

  trait OutMsg

  case class VoiceRecordingStarted(
    meetingId: IntMeetingId, recorded: Recorded,
    recordingFile: String, timestamp: String, confNum: String) extends OutMsg
  case class VoiceRecordingStopped(
    meetingId: IntMeetingId, recorded: Recorded,
    recordingFile: String, timestamp: String, confNum: String) extends OutMsg
  case class RecordingStatusChanged(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, recording: Boolean) extends OutMsg
  case class GetRecordingStatusReply(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, recording: Boolean) extends OutMsg
  case class MeetingCreatedEventOutMsg(meetingId: IntMeetingId, mProps: MeetingProperties2x) extends OutMsg
  case class MeetingMuted(
    meetingId: IntMeetingId, recorded: Recorded, meetingMuted: Boolean) extends OutMsg
  case class MeetingEnded(
    meetingId: IntMeetingId, recorded: Recorded, voiceBridge: String) extends OutMsg
  case class GetMeetingStateReply(
    meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId,
    permissions: Permissions, meetingMuted: Boolean) extends OutMsg
  case class MeetingHasEnded(
    meetingId: IntMeetingId, userId: IntUserId) extends OutMsg
  case class MeetingDestroyed(
    meetingId: IntMeetingId) extends OutMsg
  case class DisconnectAllUsers(
    meetingId: IntMeetingId) extends OutMsg
  case class DisconnectUser2x(
    meetingId: IntMeetingId, userId: IntUserId) extends OutMsg
  case class DisconnectUser(
    meetingId: IntMeetingId, userId: IntUserId) extends OutMsg
  case class KeepAliveMsgReply(
    aliveID: String) extends OutMsg
  case class PubSubPong(
    system: String, timestamp: Long) extends OutMsg
  case object IsAliveMsg$ extends OutMsg

  // Breakout Rooms
  case class BreakoutRoomsListOutMsg(
    meetingId: IntMeetingId, rooms: Vector[BreakoutRoomBody]) extends OutMsg
  case class CreateBreakoutRoom(
    meetingId: IntMeetingId, recorded: Recorded, room: BreakoutRoomOutPayload) extends OutMsg
  case class EndBreakoutRoom(
    breakoutId: IntMeetingId) extends OutMsg
  case class BreakoutRoomOutPayload(
    breakoutId: IntMeetingId, name: Name, parentId: IntMeetingId,
    voiceConfId: VoiceConf, durationInMinutes: Int, moderatorPassword: String, viewerPassword: String,
    defaultPresentationURL: String)
  case class BreakoutRoomJoinURLOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, breakoutId: IntMeetingId, userId: IntUserId, joinURL: String) extends OutMsg
  case class BreakoutRoomStartedOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, breakout: BreakoutRoomBody) extends OutMsg
  case class BreakoutRoomBody(
    name: String, breakoutId: IntMeetingId)
  case class UpdateBreakoutUsersOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, breakoutId: IntMeetingId, users: Vector[BreakoutUser]) extends OutMsg
  case class MeetingTimeRemainingUpdate(
    meetingId: IntMeetingId, recorded: Recorded, timeRemaining: Int) extends OutMsg
  case class BreakoutRoomsTimeRemainingUpdateOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, timeRemaining: Int) extends OutMsg
  case class BreakoutRoomEndedOutMsg(
    meetingId: IntMeetingId, breakoutId: IntMeetingId) extends OutMsg

  // Permissions
  case class PermissionsSettingInitialized(
    meetingId: IntMeetingId, permissions: Permissions, applyTo: Array[UserVO]) extends OutMsg
  case class NewPermissionsSetting(
    meetingId: IntMeetingId, setByUser: IntUserId, permissions: Permissions, applyTo: Array[UserVO]) extends OutMsg
  case class UserLocked(
    meetingId: IntMeetingId, userId: String, lock: Boolean) extends OutMsg
  case class GetPermissionsSettingReply(
    meetingId: IntMeetingId, userId: String) extends OutMsg

  // Users
  case class UserRegisteredEvent2x(
    meetingId: IntMeetingId, recorded: Recorded, user: RegisteredUser2x) extends OutMsg
  case class UserRegistered(
    meetingId: IntMeetingId, recorded: Recorded, user: RegisteredUser) extends OutMsg
  case class UserLeftEventOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, userId: IntUserId) extends OutMsg
  case class UserEjectedFromMeetingEventOutMsg(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, ejectedBy: IntUserId) extends OutMsg
  case class PresenterAssignedEventOutMsg(
    meetingId: IntMeetingId, recorded: Recorded,
    presenter: Presenter) extends OutMsg
  case class EjectAllVoiceUsers(
    meetingId: IntMeetingId, recorded: Recorded,
    voiceBridge: VoiceConf) extends OutMsg
  case class EndAndKickAll(
    meetingId: IntMeetingId, recorded: Recorded) extends OutMsg
  case class GetUsersReply(
    meetingId: IntMeetingId, requesterId: IntUserId,
    users: Array[UserVO]) extends OutMsg
  case class ValidateAuthTokenTimedOut(
    meetingId: IntMeetingId, requesterId: IntUserId,
    token: String, valid: Boolean, correlationId: String) extends OutMsg
  case class ValidateAuthTokenSuccessReplyOutMsg(
    meetingId: IntMeetingId, userId: IntUserId, name: Name, roles: Set[Role2x],
    extUserId: ExtUserId, authToken: AuthToken, avatar: Avatar,
    logoutUrl: LogoutUrl,
    welcome: Welcome,
    dialNumbers: Set[DialNumber],
    config: String,
    extData: String) extends OutMsg
  case class ValidateAuthTokenReply2x(
    meetingId: IntMeetingId, requesterId: IntUserId,
    token: AuthToken, valid: Boolean) extends OutMsg
  case class ValidateAuthTokenFailedReply2x(
    meetingId: IntMeetingId, requesterId: IntUserId,
    token: AuthToken, valid: Boolean, correlationId: String) extends OutMsg
  case class ValidateAuthTokenReply(
    meetingId: IntMeetingId, requesterId: IntUserId,
    token: AuthToken, valid: Boolean, correlationId: String) extends OutMsg
  case class UserJoinedEvent2x(
    meetingId: IntMeetingId, recorded: Recorded, user: User) extends OutMsg
  case class UserJoined(
    meetingId: IntMeetingId, recorded: Recorded, user: UserVO) extends OutMsg
  case class UserChangedEmojiStatus(
    meetingId: IntMeetingId, recorded: Recorded,
    emojiStatus: EmojiStatus, userId: IntUserId) extends OutMsg
  case class UserListeningOnly2x(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, presenceId: ClientId,
    voice: Voice4x) extends OutMsg
  case class UserListeningOnly(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, listenOnly: Boolean) extends OutMsg
  case class UserSharedWebcam(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, stream: String) extends OutMsg
  case class UserUnsharedWebcam(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, stream: String) extends OutMsg
  case class UserStatusChange(
    meetingId: IntMeetingId, recorded: Recorded,
    userId: IntUserId, status: String, value: Object) extends OutMsg
  case class GetUsersInVoiceConference(
    meetingId: IntMeetingId, recorded: Recorded,
    voiceConfId: VoiceConf) extends OutMsg
  case class MuteVoiceUser(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId,
    userId: IntUserId, voiceConfId: VoiceConf, voiceUserId: VoiceUserId, mute: Boolean) extends OutMsg
  case class UserVoiceMuted(
    meetingId: IntMeetingId, recorded: Recorded,
    confNum: VoiceConf, user: UserVO) extends OutMsg
  case class UserVoiceTalking(
    meetingId: IntMeetingId, recorded: Recorded,
    confNum: VoiceConf, user: UserVO) extends OutMsg
  case class EjectVoiceUser(
    meetingId: IntMeetingId, recorded: Recorded,
    requesterId: IntUserId, userId: IntUserId, voiceConfId: VoiceConf,
    voiceUserId: VoiceUserId) extends OutMsg
  case class TransferUserToMeeting(
    voiceConfId: VoiceConf, targetVoiceConfId: VoiceConf,
    userId: VoiceUserId) extends OutMsg
  case class UserJoinedVoice(
    meetingId: IntMeetingId, recorded: Recorded,
    confNum: VoiceConf, user: UserVO) extends OutMsg
  case class UserLeftVoice(
    meetingId: IntMeetingId, recorded: Recorded,
    confNum: VoiceConf, user: UserVO) extends OutMsg

  // Voice
  case class IsMeetingMutedReply(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, meetingMuted: Boolean) extends OutMsg
  case class StartRecording(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId) extends OutMsg
  case class StartRecordingVoiceConf(
    meetingId: IntMeetingId, recorded: Recorded, voiceConfId: VoiceConf) extends OutMsg
  case class StopRecordingVoiceConf(
    meetingId: IntMeetingId, recorded: Recorded, voiceConfId: VoiceConf, recordedStream: String) extends OutMsg
  case class StopRecording(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId) extends OutMsg

  // Chat
  case class GetChatHistoryReply(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId,
    replyTo: String, history: Array[Map[String, String]]) extends OutMsg
  case class SendPublicMsgEvent(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId,
    message: Map[String, String]) extends OutMsg
  case class SendPrivateMsgEvent(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId,
    message: Map[String, String]) extends OutMsg

  // Layout
  case class GetCurrentLayoutReply(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, layoutID: String,
    locked: Boolean, setByUserId: IntUserId) extends OutMsg
  case class BroadcastLayoutEvent(meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId,
    layoutID: String, locked: Boolean, setByUserId: IntUserId, applyTo: Array[UserVO]) extends OutMsg
  case class LockLayoutEvent(meetingId: IntMeetingId, recorded: Recorded, setById: IntUserId, locked: Boolean,
    applyTo: Array[UserVO]) extends OutMsg

  // Presentation
  case class ClearPresentationOutMsg(
    meetingId: IntMeetingId, recorded: Recorded) extends OutMsg
  case class RemovePresentationOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, presentationId: PresentationId) extends OutMsg
  //  case class GetPresentationInfoOutMsg(meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId,
  //    info: CurrentPresentationInfo, replyTo: ReplyTo) extends IOutMessage
  case class SendCursorUpdateOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, xPercent: Double, yPercent: Double) extends OutMsg
  case class ResizeAndMoveSlideOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, page: Page) extends OutMsg
  case class GotoSlideOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, page: Page) extends OutMsg
  case class SharePresentationOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, presentation: Presentation) extends OutMsg
  case class GetSlideInfoOutMsg(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, page: Page, replyTo: String) extends OutMsg
  case class GetPreuploadedPresentationsOutMsg(
    meetingId: IntMeetingId, recorded: Recorded) extends OutMsg
  case class PresentationConversionProgress(
    meetingId: IntMeetingId, messageKey: String, code: String,
    presentationId: PresentationId, presentationName: String) extends OutMsg
  case class PresentationConversionError(
    meetingId: IntMeetingId, messageKey: String, code: String,
    presentationId: PresentationId, numberOfPages: Int, maxNumberPages: Int, presentationName: String) extends OutMsg
  case class PresentationPageGenerated(
    meetingId: IntMeetingId, messageKey: String, code: String, presentationId: PresentationId,
    numberOfPages: Int, pagesCompleted: Int, presentationName: String) extends OutMsg
  case class PresentationConversionDone(
    meetingId: IntMeetingId, recorded: Recorded, messageKey: String, code: String,
    presentation: Presentation) extends OutMsg
  case class PresentationChanged(
    meetingId: IntMeetingId, presentation: Presentation) extends OutMsg
  case class GetPresentationStatusReply(
    meetingId: IntMeetingId, presentations: Seq[Presentation], current: Presentation, replyTo: String) extends OutMsg
  case class PresentationRemoved(
    meetingId: IntMeetingId, presentationId: String) extends OutMsg
  case class PageChanged(
    meetingId: IntMeetingId, page: Page) extends OutMsg

  // Polling
  //case class PollCreatedMessage(meetingID: String, recorded: Boolean, requesterId: String, pollId: String, poll: PollVO) extends IOutMessage
  //case class CreatePollReplyMessage(meetingID: String, recorded: Boolean, result: RequestResult, requesterId: String, pollId: String, pollType: String) extends IOutMessage
  case class PollStartedMsg(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, pollId: String, poll: SimplePollOutVO) extends OutMsg
  case class PollStoppedMsg(
    meetingId: IntMeetingId, recorded: Recorded,
    requesterId: IntUserId, pollId: String) extends OutMsg
  case class PollShowResultMsg(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, pollId: String, poll: SimplePollResultOutVO) extends OutMsg
  case class PollHideResultMsg(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, pollId: String) extends OutMsg
  case class UserRespondedToPollMsg(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, pollId: String, poll: SimplePollResultOutVO) extends OutMsg
  case class GetCurrentPollReplyMsg(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, hasPoll: Boolean, poll: Option[PollVO]) extends OutMsg

  // Whiteboard
  case class GetWhiteboardShapesReply(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, whiteboardId: String, shapes: Array[AnnotationVO], replyTo: String) extends OutMsg
  case class SendWhiteboardAnnotationEvent(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, whiteboardId: String, shape: AnnotationVO) extends OutMsg
  case class ClearWhiteboardEvent(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, whiteboardId: String) extends OutMsg
  case class UndoWhiteboardEvent(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, whiteboardId: String, shapeId: String) extends OutMsg
  case class WhiteboardEnabledEvent(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, enable: Boolean) extends OutMsg
  case class IsWhiteboardEnabledReply(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, enabled: Boolean, replyTo: String) extends OutMsg
  case class GetAllMeetingsReply(
    meetings: Array[MeetingInfo]) extends OutMsg

  // Chat
  case class SendCaptionHistoryReply(
    meetingId: IntMeetingId, recorded: Recorded, requesterId: IntUserId, history: Map[String, Array[String]]) extends OutMsg
  case class UpdateCaptionOwnerReply(
    meetingId: IntMeetingId, recorded: Recorded, locale: String, ownerID: String) extends OutMsg
  case class EditCaptionHistoryReply(
    meetingId: IntMeetingId, recorded: Recorded, userID: String, startIndex: Integer, endIndex: Integer, locale: String, text: String) extends OutMsg
  // DeskShare
  case class DeskShareStartRTMPBroadcast(conferenceName: String, streamPath: String) extends OutMsg
  case class DeskShareStopRTMPBroadcast(conferenceName: String, streamPath: String) extends OutMsg
  case class DeskShareNotifyViewersRTMP(meetingID: String, streamPath: String, videoWidth: Int, videoHeight: Int, broadcasting: Boolean) extends OutMsg
  case class DeskShareNotifyASingleViewer(meetingID: String, userID: String, streamPath: String, videoWidth: Int, videoHeight: Int, broadcasting: Boolean) extends OutMsg
  case class DeskShareHangUp(meetingID: String, fsConferenceName: String) extends OutMsg

  // Value Objects
  case class MeetingVO(id: String, recorded: Recorded)

}