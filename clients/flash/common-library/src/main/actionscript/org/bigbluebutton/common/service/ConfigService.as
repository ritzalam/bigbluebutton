package org.bigbluebutton.common.service
{
  import flash.events.Event;
  import flash.events.HTTPStatusEvent;
  import flash.events.IOErrorEvent;
  import flash.net.URLLoader;
  import flash.net.URLRequest;
  import flash.net.URLRequestMethod;
  import flash.net.URLVariables;
  
  import org.bigbluebutton.common.domain.Config;
  import org.bigbluebutton.common.model.IConfigModel;

  public class ConfigService implements IConfigService
  {
    public static const LOG:String = "ConfigService - ";
    
    [Inject]
    public var configModel:IConfigModel;
      
    private var urlLoader:URLLoader;
    private var reqVars:URLVariables = new URLVariables();
    
    public function loadConfig(serverUrl:String, sessionToken:String):void {
      trace(LOG + "sessionToken=" + sessionToken);
      reqVars.sessionToken = sessionToken;
      
      urlLoader = new URLLoader();
      
      var configUrl:String = serverUrl + "/bigbluebutton/api/configXML";
      
      var request:URLRequest = new URLRequest(configUrl);
      request.method = URLRequestMethod.GET;
      request.data = reqVars;
      
      urlLoader.addEventListener(Event.COMPLETE, handleComplete);
      urlLoader.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
      urlLoader.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
      urlLoader.load(request);	
      
    }
    
    private function httpStatusHandler(event:HTTPStatusEvent):void {
      trace(LOG + "httpStatusHandler: {0}", [event]);
    }
    
    private function ioErrorHandler(event:IOErrorEvent):void {
      trace(LOG + "ioErrorHandler: {0}", [event]);
    }
    
    private function handleComplete(e:Event):void {
      configModel.setConfig(new Config(new XML(e.target.data)));
    }
  }
}