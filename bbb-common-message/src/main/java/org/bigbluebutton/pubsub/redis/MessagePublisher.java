package org.bigbluebutton.pubsub.redis;

import org.bigbluebutton.pubsub.redis.MessageSender;
import org.bigbluebutton.common.messages.*;

import java.util.Map;

public class MessagePublisher {

  private MessageSender sender;

  public void setMessageSender(MessageSender sender) {
    this.sender = sender;
  }

  public void send(IBigBlueButtonMessage msg) {
    sender.send(msg.getChannel(), msg.toJson());
  }

  public void send(String channel, String json) {
    sender.send(channel, json);
  }

  // Polling
  private void votePoll(String meetingId, String userId, String pollId, Integer questionId, Integer answerId) {
    VotePollUserRequestMessage msg = new VotePollUserRequestMessage(meetingId, userId, pollId, questionId, answerId);
  }

  private void sendPollingMessage(String json) {
    sender.send(MessagingConstants.TO_POLLING_CHANNEL, json);
  }

  private void startPoll(String meetingId, String requesterId, String pollId, String pollType) {
    StartPollRequestMessage msg = new StartPollRequestMessage(meetingId, requesterId, pollId, pollType);

  }

  private void stopPoll(String meetingId, String userId, String pollId) {
    StopPollRequestMessage msg = new StopPollRequestMessage(meetingId, userId, pollId);
  }

  private void showPollResult(String meetingId, String requesterId, String pollId, Boolean show) {
    ShowPollResultRequestMessage msg = new ShowPollResultRequestMessage(meetingId, requesterId, pollId, show);

  }

  private void initLockSettings(String meetingID, Map<String, Boolean> permissions) {
    InitPermissionsSettingMessage msg = new InitPermissionsSettingMessage(meetingID, permissions);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void sendLockSettings(String meetingID, String userId, Map<String, Boolean> settings) {
    SendLockSettingsMessage msg = new SendLockSettingsMessage(meetingID, userId, settings);
    sender.send(MessagingConstants.TO_MEETING_CHANNEL, msg.toJson());
  }

  private void getLockSettings(String meetingId, String userId) {
    GetLockSettingsMessage msg = new GetLockSettingsMessage(meetingId, userId);
    sender.send(MessagingConstants.TO_MEETING_CHANNEL, msg.toJson());
  }

  private void lockUser(String meetingId, String requesterID, boolean lock, String internalUserID) {
    LockUserMessage msg = new LockUserMessage(meetingId, requesterID, lock, internalUserID);
    sender.send(MessagingConstants.TO_MEETING_CHANNEL, msg.toJson());
  }

  private void validateAuthToken(String meetingId, String userId, String token, String correlationId, String sessionId) {
    ValidateAuthTokenMessage msg = new ValidateAuthTokenMessage(meetingId, userId, token, correlationId, sessionId);
    sender.send(MessagingConstants.TO_MEETING_CHANNEL, msg.toJson());
  }

  private void userEmojiStatus(String meetingId, String userId, String emojiStatus) {
    UserEmojiStatusMessage msg = new UserEmojiStatusMessage(meetingId, userId, emojiStatus);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void shareWebcam(String meetingId, String userId, String stream) {
    UserShareWebcamRequestMessage msg = new UserShareWebcamRequestMessage(meetingId, userId, stream);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void unshareWebcam(String meetingId, String userId, String stream) {
    UserUnshareWebcamRequestMessage msg = new UserUnshareWebcamRequestMessage(meetingId, userId, stream);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void setUserStatus(String meetingId, String userId, String status, Object value) {
    SetUserStatusRequestMessage msg = new SetUserStatusRequestMessage(meetingId, userId, status, value.toString());
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void getUsers(String meetingId, String requesterId) {
    GetUsersRequestMessage msg = new GetUsersRequestMessage(meetingId, requesterId);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void userLeft(String meetingId, String userId, String sessionId) {
    UserLeavingMessage msg = new UserLeavingMessage(meetingId, userId);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void assignPresenter(String meetingId, String newPresenterID, String newPresenterName, String assignedBy) {
    AssignPresenterRequestMessage msg = new AssignPresenterRequestMessage(meetingId, newPresenterID, newPresenterName, assignedBy);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void setRecordingStatus(String meetingId, String userId, Boolean recording) {
    SetRecordingStatusRequestMessage msg = new SetRecordingStatusRequestMessage(meetingId, userId, recording);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void getRecordingStatus(String meetingId, String userId) {
    GetRecordingStatusRequestMessage msg = new GetRecordingStatusRequestMessage(meetingId, userId);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void initAudioSettings(String meetingID, String requesterID, Boolean muted) {
    InitAudioSettingsMessage msg = new InitAudioSettingsMessage(meetingID, requesterID, muted);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void muteAllExceptPresenter(String meetingID, String requesterID, Boolean mute) {
    MuteAllExceptPresenterRequestMessage msg = new MuteAllExceptPresenterRequestMessage(meetingID, requesterID, mute);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  public void muteAllUsers(String meetingID, String requesterID, Boolean mute) {
    MuteAllRequestMessage msg = new MuteAllRequestMessage(meetingID, requesterID, mute);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void isMeetingMuted(String meetingID, String requesterID) {
    IsMeetingMutedRequestMessage msg = new IsMeetingMutedRequestMessage(meetingID, requesterID);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void muteUser(String meetingID, String requesterID, String userID, Boolean mute) {
    MuteUserRequestMessage msg = new MuteUserRequestMessage(meetingID, requesterID, userID, mute);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());

  }

  private void lockMuteUser(String meetingID, String requesterID, String userID, Boolean lock) {
    LockMuteUserRequestMessage msg = new LockMuteUserRequestMessage(meetingID, requesterID, userID, lock);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void ejectUserFromVoice(String meetingID, String userId, String ejectedBy) {
    EjectUserFromVoiceRequestMessage msg = new EjectUserFromVoiceRequestMessage(meetingID, ejectedBy, userId);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void ejectUserFromMeeting(String meetingId, String userId, String ejectedBy) {
    EjectUserFromMeetingRequestMessage msg = new EjectUserFromMeetingRequestMessage(meetingId, userId, ejectedBy);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void removePresentation(String meetingID, String presentationID) {
    RemovePresentationMessage msg = new RemovePresentationMessage(meetingID, presentationID);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void getPresentationInfo(String meetingID, String requesterID, String replyTo) {
    GetPresentationInfoMessage msg = new GetPresentationInfoMessage(meetingID, requesterID, replyTo);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());

  }

  private void sendCursorUpdate(String meetingID, double xPercent, double yPercent) {
    SendCursorUpdateMessage msg = new SendCursorUpdateMessage(meetingID, xPercent, yPercent);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void resizeAndMoveSlide(String meetingID, double xOffset, double yOffset, double widthRatio, double heightRatio) {
    ResizeAndMoveSlideMessage msg = new ResizeAndMoveSlideMessage(meetingID, xOffset, yOffset, widthRatio, heightRatio);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void gotoSlide(String meetingID, String page) {
    GoToSlideMessage msg = new GoToSlideMessage(meetingID, page);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void sharePresentation(String meetingID, String presentationID, boolean share) {
    SharePresentationMessage msg = new SharePresentationMessage(meetingID, presentationID, share);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void getSlideInfo(String meetingID, String requesterID, String replyTo) {
    GetSlideInfoMessage msg = new GetSlideInfoMessage(meetingID, requesterID, replyTo);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void sendConversionUpdate(String messageKey, String meetingId, String code, String presId, String presName) {
    SendConversionUpdateMessage msg = new SendConversionUpdateMessage(messageKey, meetingId, code, presId, presName);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void sendPageCountError(String messageKey, String meetingId, String code, String presId, int numberOfPages, int maxNumberPages, String presName) {
    SendPageCountErrorMessage msg = new SendPageCountErrorMessage(messageKey, meetingId, code, presId, numberOfPages, maxNumberPages, presName);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void sendSlideGenerated(String messageKey, String meetingId, String code, String presId, int numberOfPages, int pagesCompleted, String presName) {
    SendSlideGeneratedMessage msg = new SendSlideGeneratedMessage(messageKey, meetingId, code, presId, numberOfPages, pagesCompleted, presName);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void sendConversionCompleted(String messageKey, String meetingId,
                                      String code, String presId, int numPages, String presName,
                                      String presBaseUrl) {
    SendConversionCompletedMessage msg = new SendConversionCompletedMessage(messageKey, meetingId,
            code, presId, numPages, presName, presBaseUrl);
    sender.send(MessagingConstants.TO_PRESENTATION_CHANNEL, msg.toJson());
  }

  private void getCurrentLayout(String meetingID, String requesterID) {
    GetCurrentLayoutRequestMessage msg = new GetCurrentLayoutRequestMessage(meetingID, requesterID);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void broadcastLayout(String meetingID, String requesterID, String layout) {
    BroadcastLayoutRequestMessage msg = new BroadcastLayoutRequestMessage(meetingID, requesterID, layout);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  private void getChatHistory(String meetingID, String requesterID, String replyTo) {
    GetChatHistoryRequestMessage msg = new GetChatHistoryRequestMessage(meetingID, requesterID, replyTo);
    sender.send(MessagingConstants.TO_CHAT_CHANNEL, msg.toJson());
  }

  private void sendPublicMessage(String meetingID, String requesterID, Map<String, String> message) {
    SendPublicChatMessage msg = new SendPublicChatMessage(meetingID, requesterID, message);
    sender.send(MessagingConstants.TO_CHAT_CHANNEL, msg.toJson());
  }

  private void sendPrivateMessage(String meetingID, String requesterID, Map<String, String> message) {
    SendPrivateChatMessage msg = new SendPrivateChatMessage(meetingID, requesterID, message);
    sender.send(MessagingConstants.TO_CHAT_CHANNEL, msg.toJson());
  }

  private void requestDeskShareInfo(String meetingID, String requesterID, String replyTo) {
    DeskShareGetInfoRequestMessage msg = new DeskShareGetInfoRequestMessage(meetingID, requesterID, replyTo);
    sender.send(MessagingConstants.FROM_VOICE_CONF_SYSTEM_CHAN, msg.toJson());
  }

  private void sendWhiteboardAnnotation(String meetingID, String requesterID, Map<String, Object> annotation) {
    SendWhiteboardAnnotationRequestMessage msg = new SendWhiteboardAnnotationRequestMessage(meetingID, requesterID, annotation);
    sender.send(MessagingConstants.TO_WHITEBOARD_CHANNEL, msg.toJson());
  }

  private void requestWhiteboardAnnotationHistory(String meetingID, String requesterID, String whiteboardId, String replyTo) {
    RequestWhiteboardAnnotationHistoryRequestMessage msg = new RequestWhiteboardAnnotationHistoryRequestMessage(meetingID, requesterID, whiteboardId, replyTo);
    sender.send(MessagingConstants.TO_WHITEBOARD_CHANNEL, msg.toJson());

  }

  private void clearWhiteboard(String meetingID, String requesterID, String whiteboardId) {
    ClearWhiteboardRequestMessage msg = new ClearWhiteboardRequestMessage(meetingID, requesterID, whiteboardId);
    sender.send(MessagingConstants.TO_WHITEBOARD_CHANNEL, msg.toJson());
  }

  private void undoWhiteboard(String meetingID, String requesterID, String whiteboardId) {
    org.bigbluebutton.common.messages.UndoWhiteboardRequest msg = new org.bigbluebutton.common.messages.UndoWhiteboardRequest(meetingID, requesterID, whiteboardId);
    sender.send(MessagingConstants.TO_WHITEBOARD_CHANNEL, msg.toJson());
  }

  private void enableWhiteboard(String meetingID, String requesterID, Boolean enable) {
    EnableWhiteboardRequestMessage msg = new EnableWhiteboardRequestMessage(meetingID, requesterID, enable);
    sender.send(MessagingConstants.TO_WHITEBOARD_CHANNEL, msg.toJson());
  }

  private void isWhiteboardEnabled(String meetingID, String requesterID, String replyTo) {
    IsWhiteboardEnabledRequestMessage msg = new IsWhiteboardEnabledRequestMessage(meetingID, requesterID, replyTo);
    sender.send(MessagingConstants.TO_WHITEBOARD_CHANNEL, msg.toJson());
  }

  private void lockLayout(String meetingID, String setById, boolean lock, boolean viewersOnly, String layout) {
    LockLayoutRequestMessage msg = new LockLayoutRequestMessage(meetingID, setById, lock, viewersOnly, layout);
    sender.send(MessagingConstants.TO_USERS_CHANNEL, msg.toJson());
  }

  // could be improved by doing some factorization
  private void getBreakoutRoomsList(String jsonMessage) {
    sender.send(MessagingConstants.TO_USERS_CHANNEL, jsonMessage);
  }

  private void createBreakoutRooms(String jsonMessage) {
    sender.send(MessagingConstants.TO_USERS_CHANNEL, jsonMessage);
  }

  private void requestBreakoutJoinURL(String jsonMessage) {
    sender.send(MessagingConstants.TO_USERS_CHANNEL, jsonMessage);
  }

  private void listenInOnBreakout(String jsonMessage) {
    sender.send(MessagingConstants.TO_USERS_CHANNEL, jsonMessage);
  }

  private void endAllBreakoutRooms(String jsonMessage) {
    sender.send(MessagingConstants.TO_USERS_CHANNEL, jsonMessage);
  }

  private void sendCaptionHistory(String meetingID, String requesterID) {
    SendCaptionHistoryRequestMessage msg = new SendCaptionHistoryRequestMessage(meetingID, requesterID);
    sender.send(MessagingConstants.TO_CAPTION_CHANNEL, msg.toJson());
  }

  private void updateCaptionOwner(String meetingID, String locale, String localeCode, String ownerID) {
    UpdateCaptionOwnerMessage msg = new UpdateCaptionOwnerMessage(meetingID, locale, localeCode, ownerID);
    sender.send(MessagingConstants.TO_CAPTION_CHANNEL, msg.toJson());
  }

  private void editCaptionHistory(String meetingID, String userID, Integer startIndex, Integer endIndex, String locale, String localeCode, String text) {
    EditCaptionHistoryMessage msg = new EditCaptionHistoryMessage(meetingID, userID, startIndex, endIndex, locale, localeCode, text);
    sender.send(MessagingConstants.TO_CAPTION_CHANNEL, msg.toJson());
  }
}
