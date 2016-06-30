package org.bigbluebutton.common.service
{
  import flash.events.Event;
  import flash.events.HTTPStatusEvent;
  import flash.events.IOErrorEvent;
  import flash.net.URLLoader;
  import flash.net.URLLoaderDataFormat;
  import flash.net.URLRequest;
  import flash.net.URLRequestMethod;
  import flash.net.URLVariables;
  
  import org.bigbluebutton.common.model.MyUserModel;
  import org.bigbluebutton.common.signal.EnterApiCallFailedSignal;
  
  import robotlegs.bender.framework.api.ILogger;

  public class EnterApiService implements IEnterApiService
  {
    [Inject]
    public var logger:ILogger;
    
    [Inject]
    public var myUserModel:MyUserModel;
    
    [Inject]
    public var enterApiFailedSignal:EnterApiCallFailedSignal;
    
    private var _url: String 
    
    public function enter(url:String, sessionToken:String):void {
      var reqVars:URLVariables = new URLVariables();
      reqVars.sessionToken = sessionToken;
      _url = url;
      fetch(url, reqVars);
    }
    
    private function fetch(url:String, reqVars: URLVariables,
                          dataFormat:String = URLLoaderDataFormat.TEXT):void {
      logger.debug("Fetching " + url);
      var urlRequest:URLRequest = new URLRequest();
      urlRequest.method = URLRequestMethod.GET;
      
      urlRequest.url = url;
      
      if (reqVars != null) {
        logger.debug("reqVars " + reqVars.toString());
        urlRequest.data = reqVars;
      }
      
      var urlLoader:URLLoader = new URLLoader();
      urlLoader.addEventListener(Event.COMPLETE, handleComplete);
      urlLoader.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
      urlLoader.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
      
      if (HTTPStatusEvent.HTTP_RESPONSE_STATUS) { 
        // only available in AIR, 
        // see http://stackoverflow.com/questions/2277650/unable-to-get-http-response-code-headers-in-actionscript-3
        urlLoader.addEventListener(HTTPStatusEvent.HTTP_RESPONSE_STATUS, httpResponseStatusHandler);
      }
      
      urlLoader.dataFormat = dataFormat;
      urlLoader.load(urlRequest);
    }
    
    private function httpResponseStatusHandler(e:HTTPStatusEvent):void {
      logger.debug("httpResponseStatusHandler from url=" + _url + " respUrl=" + e.responseURL);
    }
    
    private function httpStatusHandler(e:HTTPStatusEvent):void {
      logger.debug("httpStatusHandler from url=" + _url);
    }
    
    private function handleComplete(e:Event):void {
      logger.debug("handleComplete: " + e.target.data as String);
      var response:Object = JSON.parse(e.target.data as String);
      var result:Object = response.response;
      if (result.returncode == 'SUCCESS') {
        var user:Object = {username: result.fullname, conference: result.conference, 
          conferenceName: result.confname, externMeetingID: result.externMeetingID, 
          meetingID: result.meetingID, externUserID: result.externUserID, 
          internalUserId: result.internalUserID, role: result.role, room: result.room, 
          authToken: result.authToken, record: result.record, 
          webvoiceconf: result.webvoiceconf, dialnumber: result.dialnumber, 
          voicebridge: result.voicebridge, mode: result.mode, 
          welcome: result.welcome, logoutUrl: result.logoutUrl, 
          defaultLayout: result.defaultLayout, avatarURL: result.avatarURL};
        user.customdata = new Object();
        if (result.customdata) {
          for (var key:String in result.customdata) {
            user.customdata[key] = result.customdata[key].toString();
          }
        }
        myUserModel.load(user);
      } else {
        logger.debug("Join FAILED");
        enterApiFailedSignal.dispatch("API_ERROR");
      }
    }
    
    private function ioErrorHandler(e:IOErrorEvent):void {
      enterApiFailedSignal.dispatch("API_ERROR:IO_ERROR_EVENT");
    }
  }
}