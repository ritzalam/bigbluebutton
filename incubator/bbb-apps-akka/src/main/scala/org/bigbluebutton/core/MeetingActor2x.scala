package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.api.IncomingMessage._
import org.bigbluebutton.core.bus.{ BigBlueButtonEvent, IncomingEventBus }
import org.bigbluebutton.core.domain.MeetingProperties2x
import org.bigbluebutton.core.filters.UsersHandlerFilter
import org.bigbluebutton.core.models.MeetingStateModel
import scala.concurrent.duration._

object MeetingActorInternal2x {
  def props(mProps: MeetingProperties2x,
    eventBus: IncomingEventBus,
    outGW: OutMessageGateway): Props =
    Props(classOf[MeetingActorInternal], mProps, eventBus, outGW)
}

// This actor is an internal audit actor for each meeting actor that
// periodically sends messages to the meeting actor
class MeetingActorInternal2x(val mProps: MeetingProperties2x,
  val eventBus: IncomingEventBus, val outGW: OutMessageGateway)
    extends Actor with ActorLogging {

  import context.dispatcher
  context.system.scheduler.schedule(2 seconds, 30 seconds, self, "MonitorNumberOfWebUsers")

  // Query to get voice conference users
  //outGW.send(new GetUsersInVoiceConference(mProps.meetingID, mProps.recorded, mProps.voiceBridge))

  if (mProps.isBreakout) {
    // This is a breakout room. Inform our parent meeting that we have been successfully created.
    //eventBus.publish(BigBlueButtonEvent(
    // mProps.externalMeetingID,
    // BreakoutRoomCreated(mProps.externalMeetingID, mProps.meetingID)))
  }

  def receive = {
    case "MonitorNumberOfWebUsers" => handleMonitorNumberOfWebUsers()
  }

  def handleMonitorNumberOfWebUsers() {
    //eventBus.publish(BigBlueButtonEvent(mProps.meetingID, MonitorNumberOfUsers(mProps.meetingID)))

    // Trigger updating users of time remaining on meeting.
    //eventBus.publish(BigBlueButtonEvent(mProps.meetingID, SendTimeRemainingUpdate(mProps.meetingID)))

    //if (mProps.isBreakout) {
    // This is a breakout room. Update the main meeting with list of users in this breakout room.
    //  eventBus.publish(BigBlueButtonEvent(mProps.meetingID, SendBreakoutUsersUpdate(mProps.meetingID)))
    //}

  }
}

object MeetingActor2x {
  def props(
    props: MeetingProperties2x,
    bus: IncomingEventBus,
    outGW: OutMessageGateway,
    state: MeetingStateModel): Props =
    Props(classOf[MeetingActor2x], props, bus, outGW, state)
}

class MeetingActor2x(
    val props: MeetingProperties2x,
    val bus: IncomingEventBus,
    val outGW: OutMessageGateway,
    val state: MeetingStateModel) extends Actor with ActorLogging with UsersHandlerFilter {

  def receive = {
    case msg: RegisterUser2xCommand => handleRegisterUser2x(msg)
    case msg: ValidateAuthToken => handleValidateAuthToken2x(msg)
    case msg: NewUserPresence2x => handleUserJoinWeb2x(msg)
    case msg: EjectUserFromMeeting => handleEjectUserFromMeeting(msg)
    case msg: UserJoinedVoiceConfMessage => println("handleUserJoinedVoiceConfMessage(msg)")
    case msg: UserLeftVoiceConfMessage => println("handleUserLeftVoiceConfMessage(msg)")
    case msg: UserMutedInVoiceConfMessage => println("handleUserMutedInVoiceConfMessage(msg)")
    case msg: UserTalkingInVoiceConfMessage => println("handleUserTalkingInVoiceConfMessage(msg)")
    case msg: VoiceConfRecordingStartedMessage => println("handleVoiceConfRecordingStartedMessage(msg)")
    case msg: UserJoining => println("handleUserJoin(msg)")
    case msg: UserLeaving => println("handleUserLeft(msg)")
    case msg: AssignPresenter => println("handleAssignPresenter(msg)")
    case msg: GetUsers => println("handleGetUsers(msg)")
    case msg: ChangeUserStatus => println("handleChangeUserStatus(msg)")
    case msg: UserEmojiStatus => println("handleUserEmojiStatus(msg)")
    case msg: UserShareWebcam => println("handleUserShareWebcam(msg)")
    case msg: UserUnshareWebcam => println("handleUserunshareWebcam(msg)")
    case msg: MuteMeetingRequest => println("handleMuteMeetingRequest(msg)")
    case msg: MuteAllExceptPresenterRequest => println("handleMuteAllExceptPresenterRequest(msg)")
    case msg: IsMeetingMutedRequest => println("handleIsMeetingMutedRequest(msg)")
    case msg: MuteUserRequest => println("handleMuteUserRequest(msg)")
    case msg: EjectUserFromVoiceRequest => println("handleEjectUserRequest(msg)")
    case msg: TransferUserToMeetingRequest => println("handleTransferUserToMeeting(msg)")
    case msg: SetLockSettings => println("handleSetLockSettings(msg)")
    case msg: GetLockSettings => println("handleGetLockSettings(msg)")
    case msg: LockUserRequest => println("handleLockUserRequest(msg)")
    case msg: InitLockSettings => println("handleInitLockSettings(msg)")
    case msg: InitAudioSettings => println("handleInitAudioSettings(msg)")
    case msg: GetChatHistoryRequest => println("handleGetChatHistoryRequest(msg)")
    case msg: SendPublicMessageRequest => println("handleSendPublicMessageRequest(msg)")
    case msg: SendPrivateMessageRequest => println(".handleSendPrivateMessageRequest(msg)")
    case msg: UserConnectedToGlobalAudio => println(".handleUserConnectedToGlobalAudio(msg)")
    case msg: UserDisconnectedFromGlobalAudio => println(".handleUserDisconnectedFromGlobalAudio(msg)")
    case msg: GetCurrentLayoutRequest => println(".handleGetCurrentLayoutRequest(msg)")
    case msg: BroadcastLayoutRequest => println(".handleBroadcastLayoutRequest(msg)")
    case msg: InitializeMeeting => println(".handleInitializeMeeting(msg)")
    case msg: ClearPresentation => println(".handleClearPresentation(msg)")
    case msg: PresentationConversionUpdate => println(".handlePresentationConversionUpdate(msg)")
    case msg: PresentationPageCountError => println(".handlePresentationPageCountError(msg)")
    case msg: PresentationSlideGenerated => println(".handlePresentationSlideGenerated(msg)")
    case msg: PresentationConversionCompleted => println(".handlePresentationConversionCompleted(msg)")
    case msg: RemovePresentation => println(".handleRemovePresentation(msg)")
    case msg: GetPresentationInfo => println(".handleGetPresentationInfo(msg)")
    case msg: SendCursorUpdate => println(".handleSendCursorUpdate(msg)")
    case msg: ResizeAndMoveSlide => println(".handleResizeAndMoveSlide(msg)")
    case msg: GotoSlide => println(".handleGotoSlide(msg)")
    case msg: SharePresentation => println(".handleSharePresentation(msg)")
    case msg: GetSlideInfo => println(".handleGetSlideInfo(msg)")
    case msg: PreuploadedPresentations => println(".handlePreuploadedPresentations(msg)")
    case msg: SendWhiteboardAnnotationRequest => println(".handleSendWhiteboardAnnotationRequest(msg)")
    case msg: GetWhiteboardShapesRequest => println(".handleGetWhiteboardShapesRequest(msg)")
    case msg: ClearWhiteboardRequest => println(".handleClearWhiteboardRequest(msg)")
    case msg: UndoWhiteboardRequest => println(".handleUndoWhiteboardRequest(msg)")
    case msg: EnableWhiteboardRequest => println(".handleEnableWhiteboardRequest(msg)")
    case msg: IsWhiteboardEnabledRequest => println(".handleIsWhiteboardEnabledRequest(msg)")
    case msg: SetRecordingStatus => println(".handleSetRecordingStatus(msg)")
    case msg: GetRecordingStatus => println(".handleGetRecordingStatus(msg)")
    case msg: StartCustomPollRequest => println(".handleStartCustomPollRequest(msg)")
    case msg: StartPollRequest => println(".handleStartPollRequest(msg)")
    case msg: StopPollRequest => println(".handleStopPollRequest(msg)")
    case msg: ShowPollResultRequest => println(".handleShowPollResultRequest(msg)")
    case msg: HidePollResultRequest => println(".handleHidePollResultRequest(msg)")
    case msg: RespondToPollRequest => println(".handleRespondToPollRequest(msg)")
    case msg: GetPollRequest => println(".handleGetPollRequest(msg)")
    case msg: GetCurrentPollRequest => println(".handleGetCurrentPollRequest(msg)")
    // Breakout rooms
    case msg: BreakoutRoomsListMessage => println(".handleBreakoutRoomsList(msg)")
    case msg: CreateBreakoutRooms => println(".handleCreateBreakoutRooms(msg)")
    case msg: BreakoutRoomCreated => println(".handleBreakoutRoomCreated(msg)")
    case msg: BreakoutRoomEnded => println(".handleBreakoutRoomEnded(msg)")
    case msg: RequestBreakoutJoinURLInMessage => println(".handleRequestBreakoutJoinURL(msg)")
    case msg: BreakoutRoomUsersUpdate => println(".handleBreakoutRoomUsersUpdate(msg)")
    case msg: SendBreakoutUsersUpdate => println(".handleSendBreakoutUsersUpdate(msg)")
    case msg: EndAllBreakoutRooms => println(".handleEndAllBreakoutRooms(msg)")

    case msg: ExtendMeetingDuration => println(".handleExtendMeetingDuration(msg)")
    case msg: SendTimeRemainingUpdate => println(".handleSendTimeRemainingUpdate(msg)")
    case msg: EndMeeting => println(".handleEndMeeting(msg)")

    // Closed Caption
    case msg: SendCaptionHistoryRequest => println(".handleSendCaptionHistoryRequest(msg)")
    case msg: UpdateCaptionOwnerRequest => println(".handleUpdateCaptionOwnerRequest(msg)")
    case msg: EditCaptionHistoryRequest => println(".handleEditCaptionHistoryRequest(msg)")
  }

}
