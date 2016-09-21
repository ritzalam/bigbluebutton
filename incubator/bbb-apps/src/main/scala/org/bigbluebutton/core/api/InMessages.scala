package org.bigbluebutton.core.api

import org.bigbluebutton.core.apps.breakout.BreakoutUser
import org.bigbluebutton.core.apps.chat.ChatProperties2x
import org.bigbluebutton.core.apps.presentation.{ Presentation, PreuploadedPresentation }
import org.bigbluebutton.core.apps.presentation.domain._
import org.bigbluebutton.core.domain.{ MeetingProperties2x, Role, Stream }
import org.bigbluebutton.core.apps.presentation.domain._
import org.bigbluebutton.core.apps.presentation.PreuploadedPresentation
import org.bigbluebutton.core.apps.voice.Voice4x
import org.bigbluebutton.core.apps.whiteboard.{ AnnotationVO, WhiteboardProperties2x }
import org.bigbluebutton.core.client.ClientUserAgent
import org.bigbluebutton.core.domain._

case class InMessageHeader(name: String, dest: String, senderId: Option[String], replyTo: Option[String])

object IncomingMsg {

  trait InMsg

  ///////////////////////////////////////////////////////////////////////////////
  // 2x messages
  //////////////////////////////////////////////////////////////////////////////
  case class CreateMeetingRequestInMsg2x(header: InMessageHeader, body: CreateMeetingRequestInMsgBody) extends InMsg
  case class CreateMeetingRequestInMsgBody(props: MeetingProperties2x)

  case class RegisterUserInMsg2x(header: InMessageHeader, body: RegisterUserInMsgBody2x) extends InMsg
  case class RegisterUserInMsgBody2x(meetingId: IntMeetingId, userId: IntUserId, name: Name, roles: Set[Role],
    extUserId: ExtUserId, authToken: SessionToken, avatar: Avatar, logoutUrl: LogoutUrl,
    welcome: Welcome, dialNumbers: Set[DialNumber], config: String, extData: String)

  case class AssignUserSessionTokenInMsg2x(header: InMessageHeader, body: AssignUserSessionTokenInMsgBody2x) extends InMsg
  case class AssignUserSessionTokenInMsgBody2x(meetingId: IntMeetingId, userId: IntUserId, sessionToken: SessionToken)

  case class ValidateAuthTokenInMsg2x(header: InMessageHeader, body: ValidateAuthTokenInMsgBody) extends InMsg
  case class ValidateAuthTokenInMsgBody(token: SessionToken, userAgent: UserAgent, componentId: ComponentId)

  case class RequestUserProfileInMsg2x(header: InMessageHeader, body: RequestUserProfileInMsgBody2x) extends InMsg
  case class RequestUserProfileInMsgBody2x(sessionToken: SessionToken, userAgent: ClientUserAgent)

  case class JoinMeetingUserInMsg2x(header: InMessageHeader, body: JoinMeetingUserInMsgBody2x) extends InMsg
  case class JoinMeetingUserInMsgBody2x(meetingId: IntMeetingId, senderId: IntUserId, sessionToken: SessionToken,
    sessionId: SessionId, clientId: ClientId, userAgent: ClientUserAgent)

  //////////////////////////////////////////////////////////////////////////////
  // System
  /////////////////////////////////////////////////////////////////////////////

  case class PubSubPingMessageInMsg(system: String, timestamp: Long) extends InMsg

  case class KeepAliveMessageInMsg(aliveId: String) extends InMsg

  case class IsMeetingActorAliveMessage(meetingId: String) extends InMsg

  //////////////////////////////////////////////////////////////////////////////
  // Meeting
  /////////////////////////////////////////////////////////////////////////////

  case class RequestSessionTokenInMsg(meetingId: IntMeetingId, userId: IntUserId)

  case class RevokeSessionTokenInMsg(sessionToken: SessionToken, meetingId: IntMeetingId, userId: IntUserId) extends InMsg
  case class AssignSessionTokenInMsg(sessionToken: SessionToken, meetingId: IntMeetingId, userId: IntUserId) extends InMsg

  case class RegisterSessionIdInMsg(component: ComponentId, sessionId: SessionId, sessionToken: SessionToken) extends InMsg

  case class UnregisterIdSessionInMsg(component: ComponentId, sessionId: SessionId, sessionToken: SessionToken) extends InMsg

  case class GetUserInfoFromMeetingInMsg(meetingId: IntMeetingId, sessionId: SessionId, sessionToken: SessionToken) extends InMsg
  case class GetUserInfoInMsg(sessionId: SessionId, sessionToken: SessionToken) extends InMsg

  case class CreateMeetingRequestInMsg(meetingId: IntMeetingId, mProps: MeetingProperties2x) extends InMsg

  case class EndMeetingInMsg(meetingId: IntMeetingId) extends InMsg

  case class MonitorNumberOfUsersInMsg(meetingId: IntMeetingId) extends InMsg

  case class SendTimeRemainingUpdateInMsg(meetingId: IntMeetingId) extends InMsg
  case class ExtendMeetingDuration(meetingId: IntMeetingId, userId: IntUserId) extends InMsg
  case class InitializeMeeting(meetingId: IntMeetingId, recorded: Recorded) extends InMsg
  case class DestroyMeeting(meetingId: IntMeetingId) extends InMsg
  case class StartMeeting(meetingId: IntMeetingId) extends InMsg
  case class GetAllMeetingsRequest(meetingId: IntMeetingId /** Not used. Just to satisfy trait **/ ) extends InMsg
  case class ExtendMeetingDurationInMsg(meetingId: IntMeetingId, senderId: IntUserId) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////////
  // Breakout room
  /////////////////////////////////////////////////////////////////////////////////////

  // Sent by user to request the breakout rooms list of a room
  case class BreakoutRoomsListMessage(meetingId: String) extends InMsg
  // Sent by user to request creation of breakout rooms
  case class CreateBreakoutRooms(meetingId: String, durationInMinutes: Int, rooms: Vector[BreakoutRoomInPayload]) extends InMsg
  case class BreakoutRoomInPayload(name: String, users: Vector[String])
  // Sent by user to request for a join URL in order to be able to join a breakout room
  case class RequestBreakoutJoinURLInMessage(meetingId: String, breakoutId: String, userId: String) extends InMsg
  // Sent by breakout actor to tell meeting actor that breakout room has been created.
  case class BreakoutRoomCreated(meetingId: String, breakoutRoomId: String) extends InMsg
  // Sent by breakout actor to tell meeting actor the list of users in the breakout room.
  case class BreakoutRoomUsersUpdate(meetingId: String, breakoutId: String, users: Vector[BreakoutUser]) extends InMsg
  // Send by internal actor to tell the breakout actor to send it's list of users to the main meeting actor.
  case class SendBreakoutUsersUpdate(meetingId: IntMeetingId) extends InMsg
  // Sent by user to request ending all the breakout rooms
  case class EndAllBreakoutRooms(meetingId: String) extends InMsg
  // Sent by breakout actor to tell meeting actor that breakout room has been ended
  case class BreakoutRoomEnded(meetingId: String, breakoutRoomId: String) extends InMsg
  // Sent by user actor to ask for voice conference transfer
  case class TransferUserToMeetingRequest(meetingId: IntMeetingId, targetMeetingId: IntMeetingId, userId: IntUserId) extends InMsg

  ////////////////////////////////////////////////////////////////////////////////////
  // Lock
  ///////////////////////////////////////////////////////////////////////////////////

  case class LockUser(meetingId: IntMeetingId, userId: IntUserId, lock: Boolean) extends InMsg
  case class InitLockSettings(meetingId: IntMeetingId, settings: Permissions) extends InMsg
  case class SetLockSettings(meetingId: IntMeetingId, setByUser: IntUserId, settings: Permissions) extends InMsg
  case class GetLockSettings(meetingId: IntMeetingId, userId: IntUserId) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////
  // Users
  /////////////////////////////////////////////////////////////////////////////////

  case class ValidateAuthTokenInMessage(meetingId: IntMeetingId, senderId: IntUserId, token: SessionToken) extends InMsg

  case class RegisterUserInMessage(meetingId: IntMeetingId, userId: IntUserId, name: Name, roles: Set[Role],
    extUserId: ExtUserId, authToken: SessionToken, avatar: Avatar, logoutUrl: LogoutUrl, welcome: Welcome,
    dialNumbers: Set[DialNumber], config: String, extData: String) extends InMsg

  case class RegisterUserInMsg(header: MsgHeader, body: RegisterUserInMsgBody) extends InMsg
  case class RegisterUserInMsgBody(meetingId: IntMeetingId, userId: IntUserId, name: Name, roles: Set[Role],
    extUserId: ExtUserId, authToken: SessionToken, avatar: Avatar, logoutUrl: LogoutUrl,
    welcome: Welcome, dialNumbers: Set[DialNumber], config: String, extData: String)

  case class UserJoinMeetingInMessage(meetingId: IntMeetingId, senderId: IntUserId, sessionToken: SessionToken,
    sessionId: SessionId, clientId: ClientId, userAgent: ClientUserAgent) extends InMsg

  case class UserLeaveMeetingInMessage(meetingId: IntMeetingId, senderId: IntUserId, sessionId: SessionId,
    presenceId: ClientId) extends InMsg

  case class UserLogoutMeetingInMessage(meetingId: IntMeetingId, senderId: IntUserId, sessionId: SessionId,
    presenceId: ClientId) extends InMsg

  case class UserShareWebCamRequestInMsg(meetingId: IntMeetingId, senderId: IntUserId,
    presenceId: ClientId, replyTo: ReplyTo) extends InMsg

  case class UserViewWebCamRequestInMsg(meetingId: IntMeetingId, senderId: IntUserId,
    presenceId: ClientId, streamId: String, token: String) extends InMsg

  case class UserStartedPublishWebCamInMsg(meetingId: IntMeetingId, senderId: IntUserId,
    presenceId: ClientId, stream: Stream) extends InMsg

  case class UserStoppedPublishWebCamInMsg(meetingId: IntMeetingId, senderId: IntUserId,
    presenceId: ClientId, streamId: String) extends InMsg

  case class GetUsersInMeetingInMsg(meetingId: IntMeetingId, senderId: IntUserId) extends InMsg

  case class UserChangeEmojiStatus(meetingId: IntMeetingId, senderId: IntUserId, emojiStatus: EmojiStatus) extends InMsg

  case class EjectUserFromMeetingInMsg(meetingId: IntMeetingId, senderId: IntUserId, ejectedBy: IntUserId) extends InMsg

  case class UserAssignPresenterInMsg(meetingId: IntMeetingId, presenterId: IntUserId, assignedBy: IntUserId) extends InMsg

  case class SetRecordingStatusInMsg(meetingId: IntMeetingId, senderId: IntUserId, recording: Boolean) extends InMsg

  case class GetRecordingStatusInMsg(meetingId: IntMeetingId, senderId: IntUserId) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////
  // Chat
  /////////////////////////////////////////////////////////////////////////////////

  case class ChatGetHistoryInMsg(meetingId: IntMeetingId, senderId: IntUserId, replyTo: String) extends InMsg

  case class ChatSendPublicMessageInMsg(meetingId: IntMeetingId, senderId: IntUserId, message: Map[String, String]) extends InMsg

  case class ChatSendPrivateMessageInMsg(meetingId: IntMeetingId, senderId: IntUserId, message: Map[String, String]) extends InMsg

  case class UserConnectedToGlobalAudio(meetingId: IntMeetingId, /** Not used. Just to satisfy trait **/ voiceConf: String,
    userid: String, name: String)
  case class SendPublicChatInMsg2x(header: InMessageHeader, body: SendPublicChatInMsgBody) extends InMsg
  case class SendPublicChatInMsgBody(chatMessage: ChatProperties2x) extends InMsg

  case class UserDisconnectedFromGlobalAudio(meetingId: IntMeetingId,
    /** Not used. Just to satisfy trait **/
    voiceConf: String, userId: IntUserId, name: Name) extends InMsg

  ///////////////////////////////////////////////////////////////////////////////////////
  // Layout
  //////////////////////////////////////////////////////////////////////////////////////

  case class GetCurrentLayoutRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg

  case class SetLayoutRequest(meetingId: IntMeetingId, requesterId: IntUserId, layoutID: String) extends InMsg

  case class LockLayoutRequest(meetingId: IntMeetingId, setById: IntUserId, lock: Boolean, viewersOnly: Boolean,
    layout: Option[String]) extends InMsg

  case class BroadcastLayoutRequest(meetingId: IntMeetingId, requesterId: IntUserId, layout: String) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////////
  // Presentation
  /////////////////////////////////////////////////////////////////////////////////////
  case class PreuploadedPresentationsEventInMessage(meetingId: IntMeetingId,
    presentations: Set[PreuploadedPresentation]) extends InMsg

  case class PresentationConversionUpdateEventInMessage(meetingId: IntMeetingId, messageKey: String,
    code: String, presentationId: PresentationId) extends InMsg

  case class PresentationPageGeneratedEventInMessage(meetingId: IntMeetingId, messageKey: String,
    code: String, presentationId: PresentationId, numberOfPages: Int, pagesCompleted: Int)
      extends InMsg

  case class PresentationPageCountErrorEventInMessage(meetingId: IntMeetingId, messageKey: String,
    code: String, presentationId: PresentationId, numberOfPages: Int, maxNumberPages: Int)
      extends InMsg

  case class PresentationConversionCompletedEventInMessage(meetingId: IntMeetingId,
    messageKey: String, code: String, presentation: Presentation) extends InMsg

  case class ClearPresentationEventInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    presentationId: PresentationId) extends InMsg

  case class RemovePresentationEventInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    presentationId: PresentationId) extends InMsg

  case class GetPresentationInfoEventInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    presentationId: PresentationId) extends InMsg

  case class SendCursorUpdateEventInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    pageId: String, xPercent: Double, yPercent: Double) extends InMsg

  case class ResizeAndMovePageEventInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    xOffset: XOffset, yOffset: YOffset, pageId: String,
    widthRatio: WidthRatio, heightRatio: HeightRatio)
      extends InMsg

  case class GoToPageInEventInMessage(meetingId: IntMeetingId, senderId: IntUserId, pageId: String)
    extends InMsg

  case class SharePresentationEventInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    presentationId: PresentationId,
    share: Boolean) extends InMsg

  case class GetPageInfoEventInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    pageId: String) extends InMsg

  /////////////////////////////////////////////////////////////////////////////////////
  // Polling
  ////////////////////////////////////////////////////////////////////////////////////

  //case class CreatePollRequest(meetingID: String, requesterId: String, pollId: String, pollType: String) extends InMessage
  case class StartCustomPollRequest(meetingId: IntMeetingId, requesterId: IntUserId, pollType: String, answers: Seq[String]) extends InMsg
  case class StartPollRequest(meetingId: IntMeetingId, requesterId: IntUserId, pollType: String) extends InMsg
  case class StopPollRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class ShowPollResultRequest(meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMsg
  case class HidePollResultRequest(meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMsg
  case class RespondToPollRequest(meetingId: IntMeetingId, requesterId: IntUserId, pollId: String, questionId: Int, answerId: Int) extends InMsg
  case class GetPollRequest(meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMsg
  case class GetCurrentPollRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg

  ///////////////////////////////////////////////////////////////////////////////////
  // Voice
  ///////////////////////////////////////////////////////////////////////////////////

  case class InitAudioSettings(meetingId: IntMeetingId, requesterId: IntUserId, muted: Boolean) extends InMsg
  case class SendVoiceUsersRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class MuteAllExceptPresenterRequest(meetingId: IntMeetingId, requesterId: IntUserId, mute: Boolean) extends InMsg
  case class MuteMeetingRequest(meetingId: IntMeetingId, requesterId: IntUserId, mute: Boolean) extends InMsg
  case class IsMeetingMutedRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class MuteUserRequest(meetingId: IntMeetingId, requesterId: IntUserId, userId: IntUserId, mute: Boolean) extends InMsg
  case class LockUserRequest(meetingId: IntMeetingId, requesterId: IntUserId, userId: IntUserId, lock: Boolean) extends InMsg
  case class EjectUserFromVoiceRequest(meetingId: IntMeetingId, userId: IntUserId, ejectedBy: IntUserId) extends InMsg
  case class VoiceUserJoinedMessage(meetingId: IntMeetingId, user: IntUserId, voiceConfId: VoiceConf,
    callerId: CallerId, muted: Boolean, talking: Boolean) extends InMsg
  case class UserJoinedVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId, userId: IntUserId, externUserId: ExtUserId, callerId: CallerId,
    muted: Muted, talking: Talking, avatarUrl: String, listenOnly: ListenOnly) extends InMsg
  case class UserLeftVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId) extends InMsg
  case class UserLockedInVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId, locked: Boolean) extends InMsg
  case class UserMutedInVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId, muted: Boolean) extends InMsg
  case class UserTalkingInVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId, talking: Boolean) extends InMsg
  case class VoiceConfRecordingStartedMessage(voiceConfId: VoiceConf, recordStream: String, recording: Boolean, timestamp: String) extends InMsg
  case class UserJoinedVoiceConf(meetingId: IntMeetingId, userId: IntUserId, presenceId: ClientId, voice: Voice4x) extends InMsg
  case class UserLeftVoiceConf(meetingId: IntMeetingId, userId: IntUserId, presenceId: ClientId) extends InMsg

  /////////////////////////////////////////////////////////////////////////////////////
  // Whiteboard
  /////////////////////////////////////////////////////////////////////////////////////

  case class SendWbAnnotationReqInMsg2x(header: InMessageHeader, body: SendWbAnnotationReqInMsgBody) extends InMsg
  case class SendWbAnnotationReqInMsgBody(props: WhiteboardProperties2x) extends InMsg

  case class SendWhiteboardAnnotationRequest(meetingId: IntMeetingId, requesterId: IntUserId, annotation: AnnotationVO) extends InMsg
  case class GetWhiteboardShapesRequest(meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String, replyTo: String) extends InMsg
  case class ClearWhiteboardRequest(meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String) extends InMsg
  case class UndoWhiteboardRequest(meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String) extends InMsg
  case class EnableWhiteboardRequest(meetingId: IntMeetingId, requesterId: IntUserId, enable: Boolean) extends InMsg
  case class IsWhiteboardEnabledRequest(meetingId: IntMeetingId, requesterId: IntUserId, replyTo: String) extends InMsg

  // Caption
  case class SendCaptionHistoryRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class UpdateCaptionOwnerRequest(meetingId: IntMeetingId, locale: String, ownerID: String) extends InMsg
  case class EditCaptionHistoryRequest(meetingId: IntMeetingId, userID: String, startIndex: Integer, endIndex: Integer, locale: String, text: String) extends InMsg
  // DeskShare
  case class DeskShareStartedRequest(conferenceName: String, callerId: String, callerIdName: String)
  case class DeskShareStoppedRequest(conferenceName: String, callerId: String, callerIdName: String)
  case class DeskShareRTMPBroadcastStartedRequest(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String)
  case class DeskShareRTMPBroadcastStoppedRequest(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String)
  case class DeskShareGetDeskShareInfoRequest(conferenceName: String, requesterID: String, replyTo: String)

}
