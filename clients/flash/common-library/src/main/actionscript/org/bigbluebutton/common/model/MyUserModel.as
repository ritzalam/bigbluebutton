package org.bigbluebutton.common.model
{
  import org.bigbluebutton.common.signal.EnterApiCallSuccessSignal;

  public class MyUserModel
  {
    [Inject]
    public var enterApiCallSuccessSignal:EnterApiCallSuccessSignal;
    
    public var meetingName:String;
    public var externalMeetingId:String;
    public var internalMeetingId:String;
    public var voicebridge:String;
    public var welcome:String;
    public var username:String;
    public var role:String;
    public var externalUserId:String;
    public var internalUserId:String;
    public var logoutUrl:String;
    public var record:Boolean;
    public var authToken:String;
    public var metadata:Object;
    public var muteOnStart:Boolean;
    public var avatarUrl:String;

    
    public function load(obj:Object):void {
      meetingName = obj.conferenceName;
      externalMeetingId = obj.externMeetingID;
      internalMeetingId = obj.meetingID;
      role = obj.role;
      voicebridge = obj.voicebridge;
      welcome = obj.welcome;
      username = obj.username;
      externalUserId = obj.externUserID;
      internalUserId = obj.internalUserId;
      logoutUrl = obj.logoutUrl;
      record = !(obj.record == "false");
      avatarUrl = obj.avatarURL;
      authToken = obj.authToken;
      
      metadata = new Object();
      for (var n:String in obj.metadata) {
        for (var id:String in obj.metadata[n]) {
          metadata[id] = obj.metadata[n][id];
        }
      }
      try {
        muteOnStart = (obj.muteOnStart as String).toUpperCase() == "TRUE";
      } catch (e:Error) {
        muteOnStart = false;
      }
      
      trace("************* !!!!!! MyUserModel ENTER API SUCCESS");
      
      enterApiCallSuccessSignal.dispatch();
    }
  }
}