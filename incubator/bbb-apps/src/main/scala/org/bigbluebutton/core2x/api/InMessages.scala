package org.bigbluebutton.core2x.api

import org.bigbluebutton.core2x.apps.presentation.domain.{ PresentationId }
import org.bigbluebutton.core2x.apps.presentation.{ Presentation }
import org.bigbluebutton.core2x.domain._
import spray.json.JsObject

object IncomingMsg {
  case class InMessageHeader(name: String)
  case class InHeaderAndJsonPayload(header: InMessageHeader, payload: JsObject)
  case class MessageProcessException(message: String) extends Exception(message)

  trait InMsg

  //////////////////////////////////////////////////////////////////////////////
  // System
  /////////////////////////////////////////////////////////////////////////////

  case class PubSubPing(system: String, timestamp: Long) extends InMsg
  case class IsMeetingActorAliveMessage(meetingId: String) extends InMsg
  case class KeepAliveMessage(aliveID: String) extends InMsg

  //////////////////////////////////////////////////////////////////////////////
  // Meeting
  /////////////////////////////////////////////////////////////////////////////

  case class CreateMeetingRequestInMessage(meetingId: IntMeetingId, mProps: MeetingProperties2x) extends InMsg

  case class MonitorNumberOfUsers(meetingId: IntMeetingId) extends InMsg
  case class SendTimeRemainingUpdate(meetingId: IntMeetingId) extends InMsg
  case class ExtendMeetingDuration(meetingId: IntMeetingId, userId: IntUserId) extends InMsg
  case class InitializeMeeting(meetingId: IntMeetingId, recorded: Recorded) extends InMsg
  case class DestroyMeeting(meetingId: IntMeetingId) extends InMsg
  case class StartMeeting(meetingId: IntMeetingId) extends InMsg
  case class EndMeeting(meetingId: IntMeetingId) extends InMsg
  case class LockSetting(meetingId: IntMeetingId, locked: Boolean, settings: Map[String, Boolean]) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////////
  // Breakout room
  /////////////////////////////////////////////////////////////////////////////////////

  // Sent by user to request the breakout rooms list of a room
  case class BreakoutRoomsListMessage(
    meetingId: String) extends InMsg
  // Sent by user to request creation of breakout rooms
  case class CreateBreakoutRooms(
    meetingId: String, durationInMinutes: Int,
    rooms: Vector[BreakoutRoomInPayload]) extends InMsg
  case class BreakoutRoomInPayload(
    name: String, users: Vector[String])
  // Sent by user to request for a join URL in order to be able to join a breakout room
  case class RequestBreakoutJoinURLInMessage(
    meetingId: String, breakoutId: String,
    userId: String) extends InMsg
  // Sent by breakout actor to tell meeting actor that breakout room has been created.
  case class BreakoutRoomCreated(
    meetingId: String, breakoutRoomId: String) extends InMsg
  // Sent by breakout actor to tell meeting actor the list of users in the breakout room.
  case class BreakoutRoomUsersUpdate(
    meetingId: String, breakoutId: String,
    users: Vector[BreakoutUser]) extends InMsg
  // Send by internal actor to tell the breakout actor to send it's list of users to the main meeting actor.
  case class SendBreakoutUsersUpdate(
    meetingId: IntMeetingId) extends InMsg
  // Sent by user to request ending all the breakout rooms
  case class EndAllBreakoutRooms(
    meetingId: String) extends InMsg
  // Sent by breakout actor to tell meeting actor that breakout room has been ended
  case class BreakoutRoomEnded(
    meetingId: String, breakoutRoomId: String) extends InMsg
  // Sent by user actor to ask for voice conference transfer
  case class TransferUserToMeetingRequest(
    meetingId: IntMeetingId, targetMeetingId: IntMeetingId, userId: IntUserId) extends InMsg

  ////////////////////////////////////////////////////////////////////////////////////
  // Lock
  ///////////////////////////////////////////////////////////////////////////////////

  case class LockUser(
    meetingId: IntMeetingId, userId: IntUserId, lock: Boolean) extends InMsg
  case class InitLockSettings(
    meetingId: IntMeetingId, settings: Permissions) extends InMsg
  case class SetLockSettings(
    meetingId: IntMeetingId, setByUser: IntUserId, settings: Permissions) extends InMsg
  case class GetLockSettings(
    meetingId: IntMeetingId, userId: IntUserId) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////
  // Users
  /////////////////////////////////////////////////////////////////////////////////

  case class ValidateAuthTokenInMessage(meetingId: IntMeetingId, userId: IntUserId,
    token: AuthToken) extends InMsg

  case class RegisterUserInMessage(meetingId: IntMeetingId, userId: IntUserId, name: Name, roles: Set[Role2x],
    extUserId: ExtUserId, authToken: AuthToken, avatar: Avatar, logoutUrl: LogoutUrl, welcome: Welcome,
    dialNumbers: Set[DialNumber], config: String, extData: String) extends InMsg

  case class UserJoinMeetingInMessage(meetingId: IntMeetingId, userId: IntUserId, token: AuthToken,
    sessionId: SessionId, presenceId: PresenceId,
    userAgent: PresenceUserAgent) extends InMsg

  case class UserLeave2xCommand(
    meetingId: IntMeetingId,
    userId: IntUserId,
    sessionId: SessionId,
    presenceId: PresenceId,
    userAgent: PresenceUserAgent) extends InMsg
  case class UserPresenceLeft2x(
    meetingId: IntMeetingId,
    userId: IntUserId,
    sessionId: SessionId,
    presenceId: PresenceId,
    userAgent: PresenceUserAgent) extends InMsg
  case class ShareWebCamRequest2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId) extends InMsg
  case class ViewWebCamRequest2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId, streamId: String, token: String) extends InMsg
  case class UserShareWebCam2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId, stream: String) extends InMsg
  case class UserUnShareWebCam2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId, stream: String) extends InMsg

  case class UserJoining(
    meetingId: IntMeetingId, userId: IntUserId, token: AuthToken) extends InMsg
  case class UserLeaving(
    meetingId: IntMeetingId, userId: IntUserId, sessionId: String) extends InMsg
  case class GetUsers(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class UserEmojiStatus(
    meetingId: IntMeetingId, userId: IntUserId, emojiStatus: EmojiStatus) extends InMsg
  case class EjectUserFromMeetingInMessage(
    meetingId: IntMeetingId, userId: IntUserId, ejectedBy: IntUserId) extends InMsg
  case class UserShareWebcam(
    meetingId: IntMeetingId, userId: IntUserId, stream: String) extends InMsg
  case class UserUnshareWebcam(
    meetingId: IntMeetingId, userId: IntUserId, stream: String) extends InMsg
  case class ChangeUserStatus(
    meetingId: IntMeetingId, userId: IntUserId, status: String, value: Object) extends InMsg
  case class AssignPresenter(
    meetingId: IntMeetingId, newPresenterId: IntUserId,
    newPresenterName: Name, assignedBy: IntUserId) extends InMsg
  case class SetRecordingStatus(
    meetingId: IntMeetingId, userId: IntUserId, recording: Boolean) extends InMsg
  case class GetRecordingStatus(
    meetingId: IntMeetingId, userId: IntUserId) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////
  // Chat
  /////////////////////////////////////////////////////////////////////////////////

  case class GetChatHistoryRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, replyTo: String) extends InMsg
  case class SendPublicMessageRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, message: Map[String, String]) extends InMsg
  case class SendPrivateMessageRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, message: Map[String, String]) extends InMsg
  case class UserConnectedToGlobalAudio(
    meetingId: IntMeetingId,
    /** Not used. Just to satisfy trait **/
    voiceConf: String, userId: IntUserId, name: Name) extends InMsg
  case class UserDisconnectedFromGlobalAudio(
    meetingId: IntMeetingId,
    /** Not used. Just to satisfy trait **/
    voiceConf: String, userId: IntUserId, name: Name) extends InMsg

  ///////////////////////////////////////////////////////////////////////////////////////
  // Layout
  //////////////////////////////////////////////////////////////////////////////////////

  case class GetCurrentLayoutRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class SetLayoutRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, layoutID: String) extends InMsg
  case class LockLayoutRequest(
    meetingId: IntMeetingId, setById: IntUserId, lock: Boolean, viewersOnly: Boolean,
    layout: Option[String]) extends InMsg
  case class BroadcastLayoutRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, layout: String) extends InMsg

  //////////////////////////////////////////////////////////////////////////////////////
  // Presentation
  /////////////////////////////////////////////////////////////////////////////////////
  case class PreuploadedPresentationsEventInMessage(meetingId: IntMeetingId,
    presentations: Seq[PreuploadedPresentation]) extends InMsg
  case class PreuploadedPresentation(id: PresentationId, name: String, default: Boolean)

  case class PresentationConversionUpdateEventInMessage(meetingId: IntMeetingId, messageKey: String,
    code: String, presentationId: PresentationId) extends InMsg
  case class PresentationPageGeneratedEventInMessage(meetingId: IntMeetingId, messageKey: String, code: String,
    presentationId: PresentationId, numberOfPages: Int,
    pagesCompleted: Int) extends InMsg
  case class PresentationPageCountErrorEventInMessage(meetingId: IntMeetingId, messageKey: String, code: String,
    presentationId: PresentationId, numberOfPages: Int,
    maxNumberPages: Int) extends InMsg
  case class PresentationConversionCompletedEventInMessage(meetingId: IntMeetingId, messageKey: String, code: String,
    presentation: Presentation) extends InMsg
  case class ClearPresentationInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    presentationId: PresentationId) extends InMsg
  case class RemovePresentation(meetingId: IntMeetingId, senderId: IntUserId,
    presentationId: PresentationId) extends InMsg
  case class GetPresentationInfoInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    presentationId: PresentationId) extends InMsg
  case class SendCursorUpdateInMessage(meetingId: IntMeetingId, senderId: IntUserId, pageId: String,
    xPercent: Double, yPercent: Double) extends InMsg
  case class ResizeAndMovePageInMessage(meetingId: IntMeetingId, senderId: IntUserId,
    xOffset: Double, yOffset: Double, pageId: String,
    widthRatio: Double, heightRatio: Double) extends InMsg
  case class GoToPageInMessage(meetingId: IntMeetingId, senderId: IntUserId, pageId: String) extends InMsg
  case class SharePresentationInMessage(meetingId: IntMeetingId, senderId: IntUserId, presentationId: PresentationId,
    share: Boolean) extends InMsg
  case class GetPageInfoInMessage(meetingId: IntMeetingId, senderId: IntUserId, pageId: String) extends InMsg

  /////////////////////////////////////////////////////////////////////////////////////
  // Polling
  ////////////////////////////////////////////////////////////////////////////////////

  //case class CreatePollRequest(meetingID: String, requesterId: String, pollId: String, pollType: String) extends InMessage
  case class StartCustomPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollType: String, answers: Seq[String]) extends InMsg
  case class StartPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollType: String) extends InMsg
  case class StopPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class ShowPollResultRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMsg
  case class HidePollResultRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMsg
  case class RespondToPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId,
    pollId: String, questionId: Int, answerId: Int) extends InMsg
  case class GetPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMsg
  case class GetCurrentPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg

  ///////////////////////////////////////////////////////////////////////////////////
  // Voice
  ///////////////////////////////////////////////////////////////////////////////////

  case class InitAudioSettings(
    meetingId: IntMeetingId, requesterId: IntUserId, muted: Boolean) extends InMsg
  case class SendVoiceUsersRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class MuteAllExceptPresenterRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, mute: Boolean) extends InMsg
  case class MuteMeetingRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, mute: Boolean) extends InMsg
  case class IsMeetingMutedRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class MuteUserRequest(
    meetingId: IntMeetingId, requesterId: IntUserId,
    userId: IntUserId, mute: Boolean) extends InMsg
  case class LockUserRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, userId: IntUserId, lock: Boolean) extends InMsg
  case class EjectUserFromVoiceRequest(
    meetingId: IntMeetingId, userId: IntUserId, ejectedBy: IntUserId) extends InMsg
  case class VoiceUserJoinedMessage(
    meetingId: IntMeetingId, user: IntUserId, voiceConfId: VoiceConf,
    callerId: CallerId, muted: Boolean, talking: Boolean) extends InMsg
  case class UserJoinedVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId,
    userId: IntUserId, externUserId: ExtUserId, callerId: CallerId,
    muted: Muted, talking: Talking, avatarUrl: String, listenOnly: ListenOnly) extends InMsg
  case class UserLeftVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId) extends InMsg
  case class UserLockedInVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId, locked: Boolean) extends InMsg
  case class UserMutedInVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId, muted: Boolean) extends InMsg
  case class UserTalkingInVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId, talking: Boolean) extends InMsg
  case class VoiceConfRecordingStartedMessage(
    voiceConfId: VoiceConf, recordStream: String, recording: Boolean, timestamp: String) extends InMsg
  case class UserJoinedVoiceConf(meetingId: IntMeetingId, userId: IntUserId, presenceId: PresenceId,
    voice: Voice4x) extends InMsg
  case class UserLeftVoiceConf(meetingId: IntMeetingId, userId: IntUserId, presenceId: PresenceId) extends InMsg

  /////////////////////////////////////////////////////////////////////////////////////
  // Whiteboard
  /////////////////////////////////////////////////////////////////////////////////////

  case class SendWhiteboardAnnotationRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, annotation: AnnotationVO) extends InMsg
  case class GetWhiteboardShapesRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String, replyTo: String) extends InMsg
  case class ClearWhiteboardRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String) extends InMsg
  case class UndoWhiteboardRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String) extends InMsg
  case class EnableWhiteboardRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, enable: Boolean) extends InMsg
  case class IsWhiteboardEnabledRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, replyTo: String) extends InMsg
  case class GetAllMeetingsRequest(
    meetingId: IntMeetingId /** Not used. Just to satisfy trait **/ ) extends InMsg

  // Caption
  case class SendCaptionHistoryRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMsg
  case class UpdateCaptionOwnerRequest(
    meetingId: IntMeetingId, locale: String, ownerID: String) extends InMsg
  case class EditCaptionHistoryRequest(
    meetingId: IntMeetingId, userID: String, startIndex: Integer, endIndex: Integer, locale: String, text: String) extends InMsg
  // DeskShare
  case class DeskShareStartedRequest(conferenceName: String, callerId: String, callerIdName: String)
  case class DeskShareStoppedRequest(conferenceName: String, callerId: String, callerIdName: String)
  case class DeskShareRTMPBroadcastStartedRequest(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String)
  case class DeskShareRTMPBroadcastStoppedRequest(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String)
  case class DeskShareGetDeskShareInfoRequest(conferenceName: String, requesterID: String, replyTo: String)

}