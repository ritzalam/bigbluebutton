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
  
  import org.bigbluebutton.common.model.IConfigModel;
  import org.bigbluebutton.common.model.IMeetingModel;
  import org.bigbluebutton.common.signal.ConnectFailedSignal;
  import org.bigbluebutton.common.signal.ConnectSuccessSignal;
  import org.bigbluebutton.lib.common.services.DefaultConnectionCallback;
  import org.bigbluebutton.lib.main.models.ConnectionFailedEvent;

  public class BbbAppsConnection {   
    private const LOG:String = "BbbAppsConnection -";

    [Inject]
    public var configModel:IConfigModel;
    
    [Inject]
    public var meetingModel:IMeetingModel;
    
    [Inject]
    public var connectSuccessSignal:ConnectSuccessSignal;
    
    [Inject]
    public var connectFailedSignal:ConnectFailedSignal;
    
    private var connectAttemptTimer:Timer = null;
    private var netConnection:NetConnection = null;
    private const connectionTimeout:int = 5000;
    
    private var tunnel:Boolean = false;
    
    protected var _netConnection:NetConnection;
    
    protected var _uri:String;
    
    protected var _onUserCommand:Boolean;
    
    // http://www.adobe.com/devnet/adobe-media-server/articles/real-time-collaboration.html
    public function connect():void {
      var bbbAppUrl:String = configModel.getBbbAppUrl();
      netConnection = new NetConnection();
      netConnection.addEventListener(NetStatusEvent.NET_STATUS, connectionHandler);
      netConnection.addEventListener(NetStatusEvent.NET_STATUS, netStatus);
      netConnection.addEventListener(AsyncErrorEvent.ASYNC_ERROR, netASyncError);
      netConnection.addEventListener(SecurityErrorEvent.SECURITY_ERROR, netSecurityError);
      netConnection.addEventListener(IOErrorEvent.IO_ERROR, netIOError);
      netConnection.connect(tunnel ? "rtmpt:" : "rtmp:" + bbbAppUrl);
      
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
    
    private function connectionHandler(e:NetStatusEvent):void {
      if (connectAttemptTimer) {
        connectAttemptTimer.stop();
        connectAttemptTimer = null;
      }
      
      if ("NetConnection.Connect.Success" == e.info.code)
      {
        // RTMFP or RTMP connection succeeded
      }
      else if ("NetConnection.Connect.Failed" == e.info.code)
      {
        // RTMFP or RTMP connection failed
      }
    }
    
    public function init(callback:DefaultConnectionCallback):void {
      _netConnection = new NetConnection();
      _netConnection.client = callback;
      _netConnection.addEventListener(NetStatusEvent.NET_STATUS, netStatus);
      _netConnection.addEventListener(AsyncErrorEvent.ASYNC_ERROR, netASyncError);
      _netConnection.addEventListener(SecurityErrorEvent.SECURITY_ERROR, netSecurityError);
      _netConnection.addEventListener(IOErrorEvent.IO_ERROR, netIOError);
    }
     
    public function disconnect(onUserCommand:Boolean):void {
      _onUserCommand = onUserCommand;
      _netConnection.removeEventListener(NetStatusEvent.NET_STATUS, netStatus);
      _netConnection.removeEventListener(AsyncErrorEvent.ASYNC_ERROR, netASyncError);
      _netConnection.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, netSecurityError);
      _netConnection.removeEventListener(IOErrorEvent.IO_ERROR, netIOError);
      _netConnection.close();
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
          trace(LOG + " Connection succeeded. Uri: " + _uri);
          sendConnectionSuccessEvent();
          break;
        case "NetConnection.Connect.Failed":
          trace(LOG + " Connection failed. Uri: " + _uri);
          sendConnectionFailedSignal(ConnectionFailedEvent.CONNECTION_FAILED);
          break;
        case "NetConnection.Connect.Closed":
          trace(LOG + " Connection closed. Uri: " + _uri);
          sendConnectionFailedSignal(ConnectionFailedEvent.CONNECTION_CLOSED);
          break;
        case "NetConnection.Connect.InvalidApp":
          trace(LOG + " application not found on server. Uri: " + _uri);
          sendConnectionFailedSignal(ConnectionFailedEvent.INVALID_APP);
          break;
        case "NetConnection.Connect.AppShutDown":
          trace(LOG + " application has been shutdown. Uri: " + _uri);
          sendConnectionFailedSignal(ConnectionFailedEvent.APP_SHUTDOWN);
          break;
        case "NetConnection.Connect.Rejected":
          trace(LOG + " Connection to the server rejected. Uri: " + _uri + ". Check if the red5 specified in the uri exists and is running");
          sendConnectionFailedSignal(ConnectionFailedEvent.CONNECTION_REJECTED);
          break;
        case "NetConnection.Connect.NetworkChange":
          trace("Detected network change. User might be on a wireless and temporarily dropped connection. Doing nothing. Just making a note.");
          break;
        default:
          trace(LOG + " Default status");
          sendConnectionFailedSignal(ConnectionFailedEvent.UNKNOWN_REASON);
          break;
      }
    }
    
    protected function sendConnectionSuccessEvent():void {
      
    }
    
    protected function sendConnectionFailedSignal(reason:String):void {
      
    }
    
    protected function netSecurityError(event:SecurityErrorEvent):void {
      trace(LOG + "Security error - " + event.text);
      sendConnectionFailedSignal(ConnectionFailedEvent.UNKNOWN_REASON);
    }
    
    protected function netIOError(event:IOErrorEvent):void {
      trace(LOG + "Input/output error - " + event.text);
      sendConnectionFailedSignal(ConnectionFailedEvent.UNKNOWN_REASON);
    }
    
    protected function netASyncError(event:AsyncErrorEvent):void {
      trace(LOG + "Asynchronous code error - " + event.error + " on " + _uri);
      trace(event.toString());
      sendConnectionFailedSignal(ConnectionFailedEvent.UNKNOWN_REASON);
    }
    
    public function sendMessage(service:String, onSuccess:Function, onFailure:Function, message:Object = null):void {
      trace(LOG + "SENDING MESSAGE: [" + service + "]");
      var responder:Responder = new Responder(function(result:Object):void { // On successful result
        onSuccess("SUCCESSFULLY SENT: [" + service + "].");
      }, function(status:Object):void { // status - On error occurred
        var errorReason:String = "FAILED TO SEND: [" + service + "]:";
        for (var x:Object in status) {
          errorReason += "\n - " + x + " : " + status[x];
        }
        onFailure(errorReason);
      });
      if (message == null) {
        _netConnection.call(service, responder);
      } else {
        _netConnection.call(service, responder, message);
      }
    }
  }
}