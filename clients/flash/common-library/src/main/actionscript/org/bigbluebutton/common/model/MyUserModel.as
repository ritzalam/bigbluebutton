package org.bigbluebutton.common.model
{
  import org.bigbluebutton.common.signal.EnterApiCallSuccessSignal;

  public class MyUserModel implements IMyUserModel
  {
    [Inject]
    public var enterApiCallSuccessSignal:EnterApiCallSuccessSignal;
    
    private var _meetingName:String;
    
    private var _externMeetingID:String;
    
    /**
     * The name of the conference
     */
    private var _conference:String;
    
    /**
     * The username of the local user
     */
    private var _username:String;
    
    /**
     * The role of the local user. Could be MODERATOR or VIEWER
     */
    private var _role:String;
    
    /**
     * The room unique id, as specified in the API /create call.
     */
    private var _room:String;
    
    /**
     * Voice conference bridge for the client
     */
    private var _webvoiceconf:String;
    
    /**
     * Voice conference bridge that external SIP clients use. Usually the same as webvoiceconf
     */
    private var _voicebridge:String;
    
    /**
     *  The welcome string, as passed in through the API /create call.
     */
    private var _welcome:String;
    
    private var _meetingID:String;
    
    /**
     * External unique user id.
     */
    private var _externUserID:String;
    
    /**
     * Internal unique user id.
     */
    private var _internalUserID:String;
    
    private var _logoutUrl:String;
    /**
     * The unique userid internal to bbb-client.
     */
    private var _userid:String;
    
    private var _record:Boolean;
    
    private var _authToken:String;
    
    private var _metadata:Object;
    
    private var _muteOnStart:Boolean;
    
    private var _avatarUrl:String;
    
    public function MyUserModel()
    {
    }
    
    public function load(obj:Object):void {
      _meetingName = obj.conferenceName;
      _externMeetingID = obj.externMeetingID;
      _conference = obj.conference;
      _username = obj.username;
      _role = obj.role;
      _room = obj.room;
      _webvoiceconf = obj.webvoiceconf;
      _voicebridge = obj.voicebridge;
      _welcome = obj.welcome;
      _meetingID = obj.meetingID;
      _externUserID = obj.externUserID;
      _internalUserID = obj.internalUserId;
      _logoutUrl = obj.logoutUrl;
      _record = !(obj.record == "false");
      _avatarUrl = obj.avatarURL;
      _authToken = obj.authToken;
      
      _metadata = new Object();
      for (var n:String in obj.metadata) {
        for (var id:String in obj.metadata[n]) {
          _metadata[id] = obj.metadata[n][id];
        }
      }
      try {
        _muteOnStart = (obj.muteOnStart as String).toUpperCase() == "TRUE";
      } catch (e:Error) {
        _muteOnStart = false;
      }
      
      enterApiCallSuccessSignal.dispatch();
    }
  }
}