package org.bigbluebutton.common.service
{
  import flash.events.AsyncErrorEvent;
  import flash.events.IOErrorEvent;
  import flash.events.NetStatusEvent;
  import flash.events.SecurityErrorEvent;
  import flash.events.TimerEvent;
  import flash.net.NetConnection;
  import flash.net.Responder;
  import flash.utils.Timer;
  
  import org.bigbluebutton.common.model.ConfigModel;
  import org.bigbluebutton.common.model.MeetingModel;
  import org.bigbluebutton.common.model.MyUserModel;
  import org.bigbluebutton.common.signal.ConnectFailedSignal;
  import org.bigbluebutton.common.signal.ConnectSuccessSignal;
  import org.bigbluebutton.lib.main.models.ConnectionFailedEvent;
  
  import robotlegs.bender.framework.api.ILogger;

  public class BbbAppsConnection {   
    [Inject]
    public var logger:ILogger;

    [Inject]
    public var serverCallbackHandler:ServerCallbackHandler;
    
    [Inject]
    public var configModel:ConfigModel;
    
    [Inject]
    public var meetingModel:MeetingModel;
    
    [Inject]
    public var myUserModel:MyUserModel;
    
    [Inject]
    public var connectSuccessSignal:ConnectSuccessSignal;
    
    [Inject]
    public var connectFailedSignal:ConnectFailedSignal;
    
    private var connectAttemptTimer:Timer = null;
    private var netConnection:NetConnection = null;
    private const connectionTimeout:int = 5000; 
    private var tunnel:Boolean = false;
    
    protected var _uri:String;
    
    protected var _onUserCommand:Boolean;
    
    // http://www.adobe.com/devnet/adobe-media-server/articles/real-time-collaboration.html
    public function connect():void {

      logger.debug(" Connect");
      netConnection = new NetConnection();
      netConnection.client = serverCallbackHandler;
      netConnection.addEventListener(NetStatusEvent.NET_STATUS, netStatus);
      netConnection.addEventListener(AsyncErrorEvent.ASYNC_ERROR, netASyncError);
      netConnection.addEventListener(SecurityErrorEvent.SECURITY_ERROR, netSecurityError);
      netConnection.addEventListener(IOErrorEvent.IO_ERROR, netIOError);
      
      
      var bbbAppUrl:String = configModel.getBbbAppUrl();
      var rtmpPattern:RegExp = /rtmp:/;
      
      if (tunnel) {
        bbbAppUrl = bbbAppUrl.replace(rtmpPattern, bbbAppUrl);
      }
      var uri:String = bbbAppUrl + "/" + myUserModel.internalMeetingId;
      var lockSettings:Object = {disableCam: false, disableMic: false, disablePrivateChat: false, 
        disablePublicChat: false, lockedLayout: false, lockOnJoin: false, lockOnJoinConfigurable: false};
      netConnection.connect(uri, myUserModel.username, myUserModel.role,
        myUserModel.internalMeetingId, myUserModel.voicebridge, 
        myUserModel.record, myUserModel.externalUserId,
        myUserModel.internalUserId, myUserModel.muteOnStart, lockSettings);
      
      connectAttemptTimer = new Timer(connectionTimeout, 1);
      connectAttemptTimer.addEventListener(TimerEvent.TIMER_COMPLETE, connectAttemptTimeoutHandler);
      connectAttemptTimer.start();
     
    }
    
    private function connectAttemptTimeoutHandler(e:TimerEvent):void {
      netConnection.close();
      netConnection = null;
     
      if (! tunnel) {
        tunnel = true;
        connect();
      }      
    }
    
    public function disconnect(onUserCommand:Boolean):void {
      _onUserCommand = onUserCommand;
      netConnection.removeEventListener(NetStatusEvent.NET_STATUS, netStatus);
      netConnection.removeEventListener(AsyncErrorEvent.ASYNC_ERROR, netASyncError);
      netConnection.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, netSecurityError);
      netConnection.removeEventListener(IOErrorEvent.IO_ERROR, netIOError);
      netConnection.close();
    }
    
    protected function netStatus(event:NetStatusEvent):void {
      if (connectAttemptTimer) {
        connectAttemptTimer.stop();
        connectAttemptTimer = null;
      }
      var info:Object = event.info;
      var statusCode:String = info.code;
      switch (statusCode) {
        case "NetConnection.Connect.Success":
          logger.debug(" Connection succeeded. Uri: " + _uri);
          connectSuccessSignal.dispatch();
          break;
        case "NetConnection.Connect.Failed":
          logger.debug(" Connection failed. Uri: " + _uri);
          break;
        case "NetConnection.Connect.Closed":
          logger.debug(" Connection closed. Uri: " + _uri);
          break;
        case "NetConnection.Connect.InvalidApp":
          logger.debug(" application not found on server. Uri: " + _uri);
          break;
        case "NetConnection.Connect.AppShutDown":
          logger.debug(" application has been shutdown. Uri: " + _uri);
          break;
        case "NetConnection.Connect.Rejected":
          logger.debug(" Connection to the server rejected. Uri: " + _uri + ". Check if the red5 specified in the uri exists and is running");
          break;
        case "NetConnection.Connect.NetworkChange":
          logger.debug("Detected network change. User might be on a wireless and temporarily dropped connection. Doing nothing. Just making a note.");
          break;
        default:
          logger.debug(" Default status");
          break;
      }
    }
    
    protected function sendConnectionSuccessEvent():void {
      
    }
    
    protected function sendConnectionFailedSignal(reason:String):void {
      
    }
    
    protected function netSecurityError(event:SecurityErrorEvent):void {
      logger.debug("Security error - " + event.text);
      sendConnectionFailedSignal(ConnectionFailedEvent.UNKNOWN_REASON);
    }
    
    protected function netIOError(event:IOErrorEvent):void {
      logger.debug("Input/output error - " + event.text);
      sendConnectionFailedSignal(ConnectionFailedEvent.UNKNOWN_REASON);
    }
    
    protected function netASyncError(event:AsyncErrorEvent):void {
      logger.debug("Asynchronous code error - " + event.error + " on " + _uri);
      logger.debug(event.toString());
      sendConnectionFailedSignal(ConnectionFailedEvent.UNKNOWN_REASON);
    }
    
    public function sendMessage(service:String, message:Object):void {
      logger.debug("SENDING MESSAGE: [" + service + "]");
      var responder:Responder = new Responder(function(result:Object):void { // On successful result
        trace("SUCCESSFULLY SENT: [" + service + "].");
      }, function(status:Object):void { // status - On error occurred
        var errorReason:String = "FAILED TO SEND: [" + service + "]:";
        for (var x:Object in status) {
          errorReason += "\n - " + x + " : " + status[x];
        }
        trace(errorReason);
      });
      if (message == null) {
        netConnection.call(service, responder);
      } else {
        netConnection.call(service, responder, message);
      }
    }
  }
}