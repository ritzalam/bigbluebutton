package org.bigbluebutton.common.receivers
{
  import org.bigbluebutton.common.handlers.ValidateAuthTokenReplyHandler;
  import org.bigbluebutton.common.handlers.ValidateAuthTokenTimedOutHandler;
  import org.bigbluebutton.lib.common.models.IMessageListener;
  
  import robotlegs.bender.framework.api.ILogger;
  
  public class UsersMessageHandler implements IMessageListener
  {
    [Inject]
    public var logger:ILogger;
    
    [Inject]
    public var validateAuthTokenReplyHandler:ValidateAuthTokenReplyHandler;
    
    [Inject]
    public var validateAuthTokenTimedOutHandler:ValidateAuthTokenTimedOutHandler;
    
    public function onMessage(messageName:String, message:Object):void
    {
      switch (messageName) {
        case "validateAuthTokenTimedOut":
          validateAuthTokenTimedOutHandler.handle(message);
          break;
        case "validateAuthTokenReply":
          validateAuthTokenReplyHandler.handle(message);
          break;
/*        case "voiceUserTalking":
          handleVoiceUserTalking(message);
          break;
        case "participantJoined":
          handleParticipantJoined(message);
          break;
        case "participantLeft":
          handleParticipantLeft(message);
          break;
        case "userJoinedVoice":
          handleUserJoinedVoice(message);
          break;
        case "userLeftVoice":
          handleUserLeftVoice(message);
          break;
        case "userSharedWebcam":
          handleUserSharedWebcam(message);
          break;
        case "userUnsharedWebcam":
          handleUserUnsharedWebcam(message);
          break;
        case "user_listening_only":
        case "userListeningOnly":
          handleUserListeningOnly(message);
          break;
        case "assignPresenterCallback":
          handleAssignPresenterCallback(message);
          break;
        case "voiceUserMuted":
          handleVoiceUserMuted(message);
          break;
        case "recordingStatusChanged":
          handleRecordingStatusChanged(message);
          break;
        case "joinMeetingReply":
          handleJoinedMeeting(message);
          break
        case "getUsersReply":
          handleGetUsersReply(message);
          break;
        case "getRecordingStatusReply":
          handleGetRecordingStatusReply(message);
          break;
        case "meetingHasEnded":
        case "meetingEnded":
          handleMeetingHasEnded(message);
          break;
        case "userEmojiStatus":
          handleEmojiStatus(message);
          break;

        case "meetingState":
          handleMeetingState(message);
          break;
        case "permissionsSettingsChanged":
          handlePermissionsSettingsChanged(message);
          break;
        case "meetingMuted":
          handleMeetingMuted(message);
          break;
        case "userLocked":
          handleUserLocked(message);
          break;
*/
        default:
          break;
      }
    }
  }
}