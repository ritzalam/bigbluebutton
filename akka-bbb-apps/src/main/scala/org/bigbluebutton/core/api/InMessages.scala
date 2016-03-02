package org.bigbluebutton.core.api

import org.bigbluebutton.core.models._
import org.bigbluebutton.core.MeetingProperties
import org.bigbluebutton.core.models.Role._
import spray.json.JsObject

case class InMessageHeader(name: String)
case class InHeaderAndJsonPayload(header: InMessageHeader, payload: JsObject)
case class MessageProcessException(message: String) extends Exception(message)

trait InMessage

//////////////////////////////////////////////////////////////////////////////
// System
/////////////////////////////////////////////////////////////////////////////

case class PubSubPing(system: String, timestamp: Long) extends InMessage
case class IsMeetingActorAliveMessage(meetingId: String) extends InMessage
case class KeepAliveMessage(aliveID: String) extends InMessage

//////////////////////////////////////////////////////////////////////////////
// Meeting
/////////////////////////////////////////////////////////////////////////////

case class MonitorNumberOfUsers(meetingID: String) extends InMessage
case class SendTimeRemainingUpdate(meetingId: String) extends InMessage
case class ExtendMeetingDuration(meetingId: String, userId: String) extends InMessage
case class CreateMeeting(meetingID: String, mProps: MeetingProperties) extends InMessage
case class InitializeMeeting(meetingID: String, recorded: Boolean) extends InMessage
case class DestroyMeeting(meetingID: String) extends InMessage
case class StartMeeting(meetingID: String) extends InMessage
case class EndMeeting(meetingId: String) extends InMessage
case class LockSetting(meetingID: String, locked: Boolean, settings: Map[String, Boolean]) extends InMessage

////////////////////////////////////////////////////////////////////////////////////// 
// Breakout room
/////////////////////////////////////////////////////////////////////////////////////

// Sent by user to request the breakout rooms list of a room
case class BreakoutRoomsListMessage(meetingId: String) extends InMessage
// Sent by user to request creation of breakout rooms
case class CreateBreakoutRooms(meetingId: String, durationInMinutes: Int,
  rooms: Vector[BreakoutRoomInPayload]) extends InMessage
case class BreakoutRoomInPayload(name: String, users: Vector[String])
// Sent by user to request for a join URL in order to be able to join a breakout room
case class RequestBreakoutJoinURLInMessage(meetingId: String, breakoutId: String,
  userId: String) extends InMessage
// Sent by breakout actor to tell meeting actor that breakout room has been created.
case class BreakoutRoomCreated(meetingId: String, breakoutRoomId: String) extends InMessage
// Sent by breakout actor to tell meeting actor the list of users in the breakout room.    
case class BreakoutRoomUsersUpdate(meetingId: String, breakoutId: String,
  users: Vector[BreakoutUser]) extends InMessage
// Send by internal actor to tell the breakout actor to send it's list of users to the main meeting actor.    
case class SendBreakoutUsersUpdate(meetingId: String) extends InMessage
// Sent by user to request ending all the breakout rooms
case class EndAllBreakoutRooms(meetingId: String) extends InMessage
// Sent by breakout actor to tell meeting actor that breakout room has been ended
case class BreakoutRoomEnded(meetingId: String, breakoutRoomId: String) extends InMessage
// Sent by user actor to ask for voice conference transfer 
case class TransferUserToMeetingRequest(meetingId: String, targetMeetingId: String, userId: String) extends InMessage

////////////////////////////////////////////////////////////////////////////////////
// Lock
///////////////////////////////////////////////////////////////////////////////////

case class LockUser(meetingId: IntMeetingId, userId: IntUserId, lock: Boolean) extends InMessage
case class InitLockSettings(meetingId: IntMeetingId, settings: Permissions) extends InMessage
case class SetLockSettings(meetingId: IntMeetingId, setByUser: IntUserId, settings: Permissions) extends InMessage
case class GetLockSettings(meetingId: IntMeetingId, userId: IntUserId) extends InMessage

//////////////////////////////////////////////////////////////////////////////////
// Users
/////////////////////////////////////////////////////////////////////////////////

case class ValidateAuthToken(meetingId: IntMeetingId, userId: IntUserId, token: AuthToken,
  correlationId: String, sessionId: String) extends InMessage
case class RegisterUser(meetingId: IntMeetingId, userId: IntUserId, name: Name, role: Role,
  extUserId: ExtUserId, authToken: AuthToken) extends InMessage
case class UserJoining(meetingId: IntMeetingId, userId: IntUserId, token: AuthToken) extends InMessage
case class UserLeaving(meetingId: IntMeetingId, userId: IntUserId, sessionId: String) extends InMessage
case class GetUsers(meetingID: String, requesterID: String) extends InMessage
case class UserEmojiStatus(meetingId: IntMeetingId, userId: IntUserId, emojiStatus: EmojiStatus) extends InMessage
case class EjectUserFromMeeting(meetingId: IntMeetingId, userId: IntUserId, ejectedBy: IntUserId) extends InMessage
case class UserShareWebcam(meetingId: IntMeetingId, userId: IntUserId, stream: String) extends InMessage
case class UserUnshareWebcam(meetingId: IntMeetingId, userId: IntUserId, stream: String) extends InMessage
case class ChangeUserStatus(meetingId: IntMeetingId, userId: IntUserId, status: String, value: Object) extends InMessage
case class AssignPresenter(meetingId: IntMeetingId, newPresenterId: IntUserId,
  newPresenterName: Name, assignedBy: IntUserId) extends InMessage
case class SetRecordingStatus(meetingID: String, userId: String, recording: Boolean) extends InMessage
case class GetRecordingStatus(meetingID: String, userId: String) extends InMessage

//////////////////////////////////////////////////////////////////////////////////
// Chat
/////////////////////////////////////////////////////////////////////////////////

case class GetChatHistoryRequest(meetingID: String, requesterID: String, replyTo: String) extends InMessage
case class SendPublicMessageRequest(meetingID: String, requesterID: String, message: Map[String, String]) extends InMessage
case class SendPrivateMessageRequest(meetingID: String, requesterID: String, message: Map[String, String]) extends InMessage
case class UserConnectedToGlobalAudio(meetingId: IntMeetingId,
  /** Not used. Just to satisfy trait **/
  voiceConf: String,
  userId: IntUserId, name: Name) extends InMessage
case class UserDisconnectedFromGlobalAudio(meetingId: IntMeetingId,
  /** Not used. Just to satisfy trait **/
  voiceConf: String,
  userId: IntUserId, name: Name) extends InMessage

///////////////////////////////////////////////////////////////////////////////////////
// Layout
//////////////////////////////////////////////////////////////////////////////////////

case class GetCurrentLayoutRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage
case class SetLayoutRequest(meetingId: IntMeetingId, requesterId: IntUserId, layoutID: String) extends InMessage
case class LockLayoutRequest(meetingId: IntMeetingId, setById: IntUserId, lock: Boolean, viewersOnly: Boolean,
  layout: Option[String]) extends InMessage
case class BroadcastLayoutRequest(meetingId: IntMeetingId, requesterId: IntUserId, layout: String) extends InMessage

//////////////////////////////////////////////////////////////////////////////////////
// Presentation
/////////////////////////////////////////////////////////////////////////////////////

case class ClearPresentation(meetingID: String) extends InMessage
case class RemovePresentation(meetingID: String, presentationID: String) extends InMessage
case class GetPresentationInfo(meetingID: String, requesterID: String, replyTo: String) extends InMessage
case class SendCursorUpdate(meetingID: String, xPercent: Double, yPercent: Double) extends InMessage
case class ResizeAndMoveSlide(meetingID: String, xOffset: Double, yOffset: Double,
  widthRatio: Double, heightRatio: Double) extends InMessage
case class GotoSlide(meetingID: String, page: String) extends InMessage
case class SharePresentation(meetingID: String, presentationID: String, share: Boolean) extends InMessage
case class GetSlideInfo(meetingID: String, requesterID: String, replyTo: String) extends InMessage
case class PreuploadedPresentations(meetingID: String, presentations: Seq[Presentation]) extends InMessage
case class PresentationConversionUpdate(meetingID: String, messageKey: String, code: String,
  presentationId: String, presName: String) extends InMessage
case class PresentationPageCountError(meetingID: String, messageKey: String, code: String, presentationId: String,
  numberOfPages: Int, maxNumberPages: Int, presName: String) extends InMessage
case class PresentationSlideGenerated(meetingID: String, messageKey: String, code: String, presentationId: String,
  numberOfPages: Int, pagesCompleted: Int, presName: String) extends InMessage
case class PresentationConversionCompleted(meetingID: String, messageKey: String, code: String,
  presentation: Presentation) extends InMessage

/////////////////////////////////////////////////////////////////////////////////////  
// Polling
////////////////////////////////////////////////////////////////////////////////////

//case class CreatePollRequest(meetingID: String, requesterId: String, pollId: String, pollType: String) extends InMessage
case class StartCustomPollRequest(meetingID: String, requesterId: String, pollType: String, answers: Seq[String]) extends InMessage
case class StartPollRequest(meetingID: String, requesterId: String, pollType: String) extends InMessage
case class StopPollRequest(meetingId: IntMeetingId, requesterId: IntUserId) extends InMessage
case class ShowPollResultRequest(meetingID: String, requesterId: String, pollId: String) extends InMessage
case class HidePollResultRequest(meetingID: String, requesterId: String, pollId: String) extends InMessage
case class RespondToPollRequest(meetingId: IntMeetingId, requesterId: IntUserId,
  pollId: String, questionId: Int, answerId: Int) extends InMessage
case class GetPollRequest(meetingID: String, requesterId: String, pollId: String) extends InMessage
case class GetCurrentPollRequest(meetingID: String, requesterId: String) extends InMessage

///////////////////////////////////////////////////////////////////////////////////
// Voice
///////////////////////////////////////////////////////////////////////////////////

case class InitAudioSettings(meetingID: String, requesterID: String, muted: Boolean) extends InMessage
case class SendVoiceUsersRequest(meetingID: String, requesterID: String) extends InMessage
case class MuteAllExceptPresenterRequest(meetingID: String, requesterID: String, mute: Boolean) extends InMessage
case class MuteMeetingRequest(meetingID: String, requesterID: String, mute: Boolean) extends InMessage
case class IsMeetingMutedRequest(meetingID: String, requesterID: String) extends InMessage
case class MuteUserRequest(meetingId: IntMeetingId, requesterId: IntUserId,
  userId: IntUserId, mute: Boolean) extends InMessage
case class LockUserRequest(meetingId: IntMeetingId, requesterId: IntUserId, userId: IntUserId, lock: Boolean) extends InMessage
case class EjectUserFromVoiceRequest(meetingId: IntMeetingId, userId: IntUserId, ejectedBy: IntUserId) extends InMessage
case class VoiceUserJoinedMessage(meetingID: String, user: String, voiceConfId: String,
  callerIdNum: String, callerIdName: String, muted: Boolean, talking: Boolean) extends InMessage
case class UserJoinedVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId,
  userId: IntUserId, externUserId: ExtUserId, callerId: CallerId,
  muted: Muted, talking: Talking, listenOnly: ListenOnly) extends InMessage
case class UserLeftVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId) extends InMessage
case class UserLockedInVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId, locked: Boolean) extends InMessage
case class UserMutedInVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId, muted: Boolean) extends InMessage
case class UserTalkingInVoiceConfMessage(voiceConfId: VoiceConf, voiceUserId: VoiceUserId, talking: Boolean) extends InMessage
case class VoiceConfRecordingStartedMessage(voiceConfId: VoiceConf, recordStream: String, recording: Boolean, timestamp: String) extends InMessage

/////////////////////////////////////////////////////////////////////////////////////
// Whiteboard
/////////////////////////////////////////////////////////////////////////////////////

case class SendWhiteboardAnnotationRequest(meetingID: String, requesterID: String, annotation: AnnotationVO) extends InMessage
case class GetWhiteboardShapesRequest(meetingID: String, requesterID: String, whiteboardId: String, replyTo: String) extends InMessage
case class ClearWhiteboardRequest(meetingID: String, requesterID: String, whiteboardId: String) extends InMessage
case class UndoWhiteboardRequest(meetingID: String, requesterID: String, whiteboardId: String) extends InMessage
case class EnableWhiteboardRequest(meetingID: String, requesterID: String, enable: Boolean) extends InMessage
case class IsWhiteboardEnabledRequest(meetingID: String, requesterID: String, replyTo: String) extends InMessage
case class GetAllMeetingsRequest(meetingID: String /** Not used. Just to satisfy trait **/ ) extends InMessage

// Caption
case class SendCaptionHistoryRequest(meetingID: String, requesterID: String) extends InMessage
case class UpdateCaptionOwnerRequest(meetingID: String, locale: String, ownerID: String) extends InMessage
case class EditCaptionHistoryRequest(meetingID: String, userID: String, startIndex: Integer, endIndex: Integer, locale: String, text: String) extends InMessage
