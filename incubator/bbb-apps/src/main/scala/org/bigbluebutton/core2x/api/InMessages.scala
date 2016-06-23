package org.bigbluebutton.core2x.api

import org.bigbluebutton.core2x.domain._
import spray.json.JsObject

object IncomingMessage {
  case class InMessageHeader(name: String)
  case class InHeaderAndJsonPayload(header: InMessageHeader, payload: JsObject)
  case class MessageProcessException(message: String) extends Exception(message)

  trait InMessage2x

  //////////////////////////////////////////////////////////////////////////////
  // System
  /////////////////////////////////////////////////////////////////////////////

  case class PubSubPing(system: String, timestamp: Long) extends InMessage2x
  case class IsMeetingActorAliveMessage(meetingId: String) extends InMessage2x
  case class KeepAliveMessage(aliveID: String) extends InMessage2x

  //////////////////////////////////////////////////////////////////////////////
  // Meeting
  /////////////////////////////////////////////////////////////////////////////

  case class CreateMeetingRequestInMessage(meetingId: IntMeetingId, mProps: MeetingProperties2x) extends InMessage2x

  case class MonitorNumberOfUsers(meetingId: IntMeetingId) extends InMessage2x
  case class SendTimeRemainingUpdate(meetingId: IntMeetingId) extends InMessage2x
  case class ExtendMeetingDuration(meetingId: IntMeetingId, userId: IntUserId) extends InMessage2x
  case class InitializeMeeting(meetingId: IntMeetingId, recorded: Recorded) extends InMessage2x
  case class DestroyMeeting(meetingId: IntMeetingId) extends InMessage2x
  case class StartMeeting(meetingId: IntMeetingId) extends InMessage2x
  case class EndMeeting(meetingId: IntMeetingId) extends InMessage2x
  case class LockSetting(meetingId: IntMeetingId, locked: Boolean, settings: Map[String, Boolean]) extends InMessage2x

  //////////////////////////////////////////////////////////////////////////////////////
  // Breakout room
  /////////////////////////////////////////////////////////////////////////////////////

  // Sent by user to request the breakout rooms list of a room
  case class BreakoutRoomsListMessage(
    meetingId: String) extends InMessage2x
  // Sent by user to request creation of breakout rooms
  case class CreateBreakoutRooms(
    meetingId: String, durationInMinutes: Int,
    rooms: Vector[BreakoutRoomInPayload]) extends InMessage2x
  case class BreakoutRoomInPayload(
    name: String, users: Vector[String])
  // Sent by user to request for a join URL in order to be able to join a breakout room
  case class RequestBreakoutJoinURLInMessage(
    meetingId: String, breakoutId: String,
    userId: String) extends InMessage2x
  // Sent by breakout actor to tell meeting actor that breakout room has been created.
  case class BreakoutRoomCreated(
    meetingId: String, breakoutRoomId: String) extends InMessage2x
  // Sent by breakout actor to tell meeting actor the list of users in the breakout room.
  case class BreakoutRoomUsersUpdate(
    meetingId: String, breakoutId: String,
    users: Vector[BreakoutUser]) extends InMessage2x
  // Send by internal actor to tell the breakout actor to send it's list of users to the main meeting actor.
  case class SendBreakoutUsersUpdate(
    meetingId: IntMeetingId) extends InMessage2x
  // Sent by user to request ending all the breakout rooms
  case class EndAllBreakoutRooms(
    meetingId: String) extends InMessage2x
  // Sent by breakout actor to tell meeting actor that breakout room has been ended
  case class BreakoutRoomEnded(
    meetingId: String, breakoutRoomId: String) extends InMessage2x
  // Sent by user actor to ask for voice conference transfer
  case class TransferUserToMeetingRequest(
    meetingId: IntMeetingId, targetMeetingId: IntMeetingId, userId: IntUserId) extends InMessage2x

  ////////////////////////////////////////////////////////////////////////////////////
  // Lock
  ///////////////////////////////////////////////////////////////////////////////////

  case class LockUser(
    meetingId: IntMeetingId, userId: IntUserId, lock: Boolean) extends InMessage2x
  case class InitLockSettings(
    meetingId: IntMeetingId, settings: Permissions) extends InMessage2x
  case class SetLockSettings(
    meetingId: IntMeetingId, setByUser: IntUserId, settings: Permissions) extends InMessage2x
  case class GetLockSettings(
    meetingId: IntMeetingId, userId: IntUserId) extends InMessage2x

  //////////////////////////////////////////////////////////////////////////////////
  // Users
  /////////////////////////////////////////////////////////////////////////////////

  case class ValidateAuthTokenRequestInMessage(
    meetingId: IntMeetingId,
    userId: IntUserId,
    token: AuthToken) extends InMessage2x

  case class ValidateAuthToken(
    meetingId: IntMeetingId, userId: IntUserId, token: AuthToken,
    correlationId: String, sessionId: String) extends InMessage2x
  case class RegisterUserRequestInMessage(
    meetingId: IntMeetingId, userId: IntUserId, name: Name, roles: Set[Role2x],
    extUserId: ExtUserId, authToken: AuthToken, avatar: Avatar,
    logoutUrl: LogoutUrl,
    welcome: Welcome,
    dialNumbers: Set[DialNumber],
    config: String,
    extData: String) extends InMessage2x
  case class RegisterUser(
    meetingId: IntMeetingId, userId: IntUserId, name: Name, roles: Set[String],
    extUserId: ExtUserId, authToken: AuthToken) extends InMessage2x
  case class NewUserPresence2x(
    meetingId: IntMeetingId,
    userId: IntUserId,
    token: AuthToken,
    sessionId: SessionId,
    presenceId: PresenceId,
    userAgent: PresenceUserAgent) extends InMessage2x
  case class UserLeave2xCommand(
    meetingId: IntMeetingId,
    userId: IntUserId,
    sessionId: SessionId,
    presenceId: PresenceId,
    userAgent: PresenceUserAgent) extends InMessage2x
  case class UserPresenceLeft2x(
    meetingId: IntMeetingId,
    userId: IntUserId,
    sessionId: SessionId,
    presenceId: PresenceId,
    userAgent: PresenceUserAgent) extends InMessage2x
  case class ShareWebCamRequest2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId) extends InMessage2x
  case class ViewWebCamRequest2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId, streamId: String, token: String) extends InMessage2x
  case class UserShareWebCam2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId, stream: String) extends InMessage2x
  case class UserUnShareWebCam2x(
    meetingId: IntMeetingId, userId: IntUserId,
    presenceId: PresenceId, stream: String) extends InMessage2x

  case class UserJoining(
    meetingId: IntMeetingId, userId: IntUserId, token: AuthToken) extends InMessage2x
  case class UserLeaving(
    meetingId: IntMeetingId, userId: IntUserId, sessionId: String) extends InMessage2x
  case class GetUsers(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage2x
  case class UserEmojiStatus(
    meetingId: IntMeetingId, userId: IntUserId, emojiStatus: EmojiStatus) extends InMessage2x
  case class EjectUserFromMeeting(
    meetingId: IntMeetingId, userId: IntUserId, ejectedBy: IntUserId) extends InMessage2x
  case class UserShareWebcam(
    meetingId: IntMeetingId, userId: IntUserId, stream: String) extends InMessage2x
  case class UserUnshareWebcam(
    meetingId: IntMeetingId, userId: IntUserId, stream: String) extends InMessage2x
  case class ChangeUserStatus(
    meetingId: IntMeetingId, userId: IntUserId, status: String, value: Object) extends InMessage2x
  case class AssignPresenter(
    meetingId: IntMeetingId, newPresenterId: IntUserId,
    newPresenterName: Name, assignedBy: IntUserId) extends InMessage2x
  case class SetRecordingStatus(
    meetingId: IntMeetingId, userId: IntUserId, recording: Boolean) extends InMessage2x
  case class GetRecordingStatus(
    meetingId: IntMeetingId, userId: IntUserId) extends InMessage2x

  //////////////////////////////////////////////////////////////////////////////////
  // Chat
  /////////////////////////////////////////////////////////////////////////////////

  case class GetChatHistoryRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, replyTo: String) extends InMessage2x
  case class SendPublicMessageRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, message: Map[String, String]) extends InMessage2x
  case class SendPrivateMessageRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, message: Map[String, String]) extends InMessage2x
  case class UserConnectedToGlobalAudio(
    meetingId: IntMeetingId,
    /** Not used. Just to satisfy trait **/
    voiceConf: String, userId: IntUserId, name: Name) extends InMessage2x
  case class UserDisconnectedFromGlobalAudio(
    meetingId: IntMeetingId,
    /** Not used. Just to satisfy trait **/
    voiceConf: String, userId: IntUserId, name: Name) extends InMessage2x

  ///////////////////////////////////////////////////////////////////////////////////////
  // Layout
  //////////////////////////////////////////////////////////////////////////////////////

  case class GetCurrentLayoutRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage2x
  case class SetLayoutRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, layoutID: String) extends InMessage2x
  case class LockLayoutRequest(
    meetingId: IntMeetingId, setById: IntUserId, lock: Boolean, viewersOnly: Boolean,
    layout: Option[String]) extends InMessage2x
  case class BroadcastLayoutRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, layout: String) extends InMessage2x

  //////////////////////////////////////////////////////////////////////////////////////
  // Presentation
  /////////////////////////////////////////////////////////////////////////////////////

  case class ClearPresentation(
    meetingId: IntMeetingId) extends InMessage2x
  case class RemovePresentation(
    meetingId: IntMeetingId, presentationId: PresentationId) extends InMessage2x
  case class GetPresentationInfo(
    meetingId: IntMeetingId, requesterId: IntUserId, replyTo: String) extends InMessage2x
  case class SendCursorUpdate(
    meetingId: IntMeetingId, xPercent: Double, yPercent: Double) extends InMessage2x
  case class ResizeAndMoveSlide(
    meetingId: IntMeetingId, xOffset: Double, yOffset: Double,
    widthRatio: Double, heightRatio: Double) extends InMessage2x
  case class GotoSlide(
    meetingId: IntMeetingId, page: String) extends InMessage2x
  case class SharePresentation(
    meetingId: IntMeetingId, presentationId: PresentationId, share: Boolean) extends InMessage2x
  case class GetSlideInfo(
    meetingId: IntMeetingId, requesterId: IntUserId, replyTo: String) extends InMessage2x
  case class PreuploadedPresentations(
    meetingId: IntMeetingId, presentations: Seq[Presentation]) extends InMessage2x
  case class PresentationConversionUpdate(
    meetingId: IntMeetingId, messageKey: String, code: String,
    presentationId: PresentationId, presName: String) extends InMessage2x
  case class PresentationPageCountError(
    meetingId: IntMeetingId, messageKey: String, code: String, presentationId: PresentationId,
    numberOfPages: Int, maxNumberPages: Int, presName: String) extends InMessage2x
  case class PresentationSlideGenerated(
    meetingId: IntMeetingId, messageKey: String, code: String, presentationId: PresentationId,
    numberOfPages: Int, pagesCompleted: Int, presName: String) extends InMessage2x
  case class PresentationConversionCompleted(
    meetingId: IntMeetingId, messageKey: String, code: String,
    presentation: Presentation) extends InMessage2x

  /////////////////////////////////////////////////////////////////////////////////////
  // Polling
  ////////////////////////////////////////////////////////////////////////////////////

  //case class CreatePollRequest(meetingID: String, requesterId: String, pollId: String, pollType: String) extends InMessage
  case class StartCustomPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollType: String, answers: Seq[String]) extends InMessage2x
  case class StartPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollType: String) extends InMessage2x
  case class StopPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage2x
  case class ShowPollResultRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMessage2x
  case class HidePollResultRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMessage2x
  case class RespondToPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId,
    pollId: String, questionId: Int, answerId: Int) extends InMessage2x
  case class GetPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, pollId: String) extends InMessage2x
  case class GetCurrentPollRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage2x

  ///////////////////////////////////////////////////////////////////////////////////
  // Voice
  ///////////////////////////////////////////////////////////////////////////////////

  case class InitAudioSettings(
    meetingId: IntMeetingId, requesterId: IntUserId, muted: Boolean) extends InMessage2x
  case class SendVoiceUsersRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage2x
  case class MuteAllExceptPresenterRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, mute: Boolean) extends InMessage2x
  case class MuteMeetingRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, mute: Boolean) extends InMessage2x
  case class IsMeetingMutedRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage2x
  case class MuteUserRequest(
    meetingId: IntMeetingId, requesterId: IntUserId,
    userId: IntUserId, mute: Boolean) extends InMessage2x
  case class LockUserRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, userId: IntUserId, lock: Boolean) extends InMessage2x
  case class EjectUserFromVoiceRequest(
    meetingId: IntMeetingId, userId: IntUserId, ejectedBy: IntUserId) extends InMessage2x
  case class VoiceUserJoinedMessage(
    meetingId: IntMeetingId, user: IntUserId, voiceConfId: VoiceConf,
    callerId: CallerId, muted: Boolean, talking: Boolean) extends InMessage2x
  case class UserJoinedVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId,
    userId: IntUserId, externUserId: ExtUserId, callerId: CallerId,
    muted: Muted, talking: Talking, avatarUrl: String, listenOnly: ListenOnly) extends InMessage2x
  case class UserLeftVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId) extends InMessage2x
  case class UserLockedInVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId, locked: Boolean) extends InMessage2x
  case class UserMutedInVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId, muted: Boolean) extends InMessage2x
  case class UserTalkingInVoiceConfMessage(
    voiceConfId: VoiceConf, voiceUserId: VoiceUserId, talking: Boolean) extends InMessage2x
  case class VoiceConfRecordingStartedMessage(
    voiceConfId: VoiceConf, recordStream: String, recording: Boolean, timestamp: String) extends InMessage2x
  case class UserJoinedVoiceConf(meetingId: IntMeetingId, userId: IntUserId, presenceId: PresenceId,
    voice: Voice4x) extends InMessage2x
  case class UserLeftVoiceConf(meetingId: IntMeetingId, userId: IntUserId, presenceId: PresenceId) extends InMessage2x

  /////////////////////////////////////////////////////////////////////////////////////
  // Whiteboard
  /////////////////////////////////////////////////////////////////////////////////////

  case class SendWhiteboardAnnotationRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, annotation: AnnotationVO) extends InMessage2x
  case class GetWhiteboardShapesRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String, replyTo: String) extends InMessage2x
  case class ClearWhiteboardRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String) extends InMessage2x
  case class UndoWhiteboardRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, whiteboardId: String) extends InMessage2x
  case class EnableWhiteboardRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, enable: Boolean) extends InMessage2x
  case class IsWhiteboardEnabledRequest(
    meetingId: IntMeetingId, requesterId: IntUserId, replyTo: String) extends InMessage2x
  case class GetAllMeetingsRequest(
    meetingId: IntMeetingId /** Not used. Just to satisfy trait **/ ) extends InMessage2x

  // Caption
  case class SendCaptionHistoryRequest(
    meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage2x
  case class UpdateCaptionOwnerRequest(
    meetingId: IntMeetingId, locale: String, ownerID: String) extends InMessage2x
  case class EditCaptionHistoryRequest(
    meetingId: IntMeetingId, userID: String, startIndex: Integer, endIndex: Integer, locale: String, text: String) extends InMessage2x
  // DeskShare
  case class DeskShareStartedRequest(conferenceName: String, callerId: String, callerIdName: String)
  case class DeskShareStoppedRequest(conferenceName: String, callerId: String, callerIdName: String)
  case class DeskShareRTMPBroadcastStartedRequest(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String)
  case class DeskShareRTMPBroadcastStoppedRequest(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String)
  case class DeskShareGetDeskShareInfoRequest(conferenceName: String, requesterID: String, replyTo: String)

}